/**
 * myGrid表格插件定义JS
 */
;(function(w,$){
	
	function $g(ele,opts)
	{
		this.ele = ele;
		this.options = $.extend(true,{},this.options,opts);
		this._init();
		return this;
	}
	var $grid = $g.pt = $g.prototype;
	// 默认参数
	$grid.options = 
	{
		data : '',                 // 要显示的数据
		width : '100%',            // 表格宽度  
		height : 'auto',           // 表格高度  
		idField : 'id',            // 表格数据唯一标识字段  
		rowNumbers: false,         // 是否显示行号,true:显示，false:不显示
		selectBox: false,          // 是否显示选择框，true:显示，false:不显示
		multiSelect: true,         // 是否多行选择 ,true:多行选择，false:单行选择
		select2Options:{},         // select2下拉插件配置选项 
		hideHeadWhenEmpty : false, // 当数据行为空时隐藏头部
		onDeleteColumn : function(colIndex,fieldName){}, // 当列某一列删除时触发，参数(1:列索引,2:列对应的字段名)
		onTableShow: function(){}, // 当表格显示时，如果配置了hideHeadWhenEmpty，则会在动态增加删除上时，改变表格的显示状态
		onTableHide: function(){}, // 当表格隐藏时，如果配置了hideHeadWhenEmpty，则会在动态增加删除上时，改变表格的显示状态
		headToolBar : '',   //[{iconCls:'',text:'',handler:function(){},targets:4}]
		columns : [],       //{field:'',title:'',name:'',align:'',addClass:'',width:'',minWidth:'',maxWidth:'',formatter:'',edit:false,editor:{type:'text,textarea,select,checkbox,radio,date',data:'',onChange:function(),onSelect:function(){},dateFormatter:'YYYY-MM-DD',editTitle:false,titleEditor:{type:'text,textarea,select,checkbox,radio,date',data:''.onSelect:function(){},onChange:function(),dateFormatter:'YYYY-MM-DD'}}
		// 注：overrideOptions行属性，支持在每一行数据中动态指定字段的具体类型（比如同一列中的不同行的编辑类型不一样，有下拉型，有文本型的，可以用此特性实现）、select2插件的选项信息（比如实现同一列中不同行的下拉菜单有的多选也有的单选的情况，可用此特性实现），具体实现行数动态改变列信息的配置如下：
		// row为行数据对象，则可以配置行数据，row.overrideOptions={selectOptions:{multiple:true},colEditOptions:{'字段名1':{type:'select'}}}
	};
	// 默认配置
	$grid.config = 
	{
		tableClass : 'ui-mygrid-table',
		thClass : 'ui-mygrid-head-row',
		trClass : 'ui-mygrid-body-row',
		rowClass : 'ui-mygrid-row',
		ignoreRowClass : 'ignore',
		delColBtnClass : 'del-col-btn',
		delColBtnTitle : '删除列',
		tdClass : 'ui-mygrid-col',
		rowIndexPre : 'table-row-',
		colIndexPre : 'table-col-',
		trIgnoreField : 'ignore',
		trHiddenField : 'hidden',
		rowNumberTdClass : 'rownumber',
		selectBoxTdClass : 'selectbox',
		selectedRowClass : 'selected',
		dateFormatter: 'YYYY-MM-DD hh:mm:ss',
		editorClass : 
		{
			text : 'form-control input-sm',
			textarea : 'form-control',
			select : '',
			date: 'laydate-icon form-control layer-date',
			checkboxOrRadio_label : 'checkbox-inline pd-l-0 mg-0',
//			checkboxOrRadio_label : '',
			checkboxOrRadio_input : 'ui-mygrid-iChecks'
		},
		select2Opts : 
		{
//			placeholder : ''
		},
		fixedColOptions : 
		{
			rowNumberCol : {field:'',title:'',width:'30px',align:'center',isRowNumberCol:true},
			selectBoxCol : {field:'',title:'',width:'30px',align:'center',edit:true,editor:{type:'checkbox'},isSelectBoxCol:true},
		}
	};
	// 初始化
	$grid._init = function()
	{
		this.created = this.ele.data("created");
		// 如果没有被初始化
		if(!this.created)
		{
			this.ele.data("created",true);
		}	
		this._cacheColData = {};
		this._create();
		this.ele.data("This",this);
	};
	// 创建表格
	$grid._create = function()
	{
		if(!this.options.columns || !this.options.columns.length)
		{
			return false;
		}	
		this.ele.addClass(this.config.tableClass)
			.css({width:this.options.width ? this.options.width : '100%'});
		this._beforeCreate();
		this._createHeader();
		this._createBody(this.options.data);
	};
	$grid._beforeCreate = function()
	{
		// 如果需要显示选择框
		if(this.options.selectBox)
		{
			var col = this.options.multiSelect 
				? $.extend(true,{},this.config.fixedColOptions.selectBoxCol,
						{editTitle:true,titleEditor:{type:'checkbox'}}) 
				: this.config.fixedColOptions.selectBoxCol;
			this.options.columns.splice(0,0,$.extend(true,col,
				{addClass:this.config.selectBoxTdClass}));
		}	
		// 如果需要显示行号 
		if(this.options.rowNumbers)
		{
			this.options.columns.splice(0,0,$.extend(true,{},
				this.config.fixedColOptions.rowNumberCol,
				{addClass:this.config.rowNumberTdClass}));
		}
	};
	// 创建表头
	$grid._createHeader = function()
	{
		var opts = this.options;
		var config = this.config;
		this.ele.addClass(opts.tableClass).empty();
		var $th = $("<tr/>").addClass(config.thClass)
			.addClass(config.rowClass).appendTo($("<thead>")
			.appendTo(this.ele));
		var col;
		for(var index in opts.columns)
		{
			col = opts.columns[index];
			col.index = index;
//			col.idValue = config.headColIndexPre + index;
			this._createThColumn(col,$th,col.text);
		}	
	};
	// 创建表格体
	$grid._createBody = function(data)
	{
		var row,$tbody;
		$tbody = this.ele.find("tbody").empty();
		this.rowIndex = 0; // 重置行索引
		if(!$tbody || !$tbody.length)
		{
			$tbody = $("<tbody/>").appendTo(this.ele);
		}	
		if(data && data.length)
		{
			for(var i=0;i<data.length;i++)
			{
				row = data[i];
				this._createRow($tbody,row);
			}
		}
		this._theadStatusListener();
	};
	// 创建头部列
	$grid._createThColumn = function(col,$row,data)
	{
		if(col.editTitle && col.titleEditor)
		{
			this._createEditColumn(col,col.titleEditor,$row,data);
		}else
		{
			this._createPlainColumn(col,$row,data);
		}	
	};
	// 创建数据行
	$grid._createRow = function($tbody,row,isAddRow)
	{
		var $tr = $("<tr/>").addClass(this.config.trClass)
			.addClass(this.config.rowClass)
			.attr("id",this.config.rowIndexPre + this.rowIndex)
			.attr('row-id',this.rowIndex)
			.appendTo($tbody);
		// 缓存当前行是新增的还是原始创建的
		$tr.data("isAddRow",isAddRow ? isAddRow : false);
		for(var j=0;j<this.options.columns.length;j++)
		{
			col = this.options.columns[j];
			col.index = j;
//			col.idValue = this.config.bodyColIndexPre + j;
			this._createTrColumn(col,$tr,row,true);
		}
		// 如果行需要忽略或隐藏，则不显示该行
		if(row[this.config.trIgnoreField] || row[this.config.trHiddenField])
		{
			$tr.addClass(this.config.ignoreRowClass);
		}	
		// 行号自增长
		this.rowIndex++;
	};
	// 创建数据列
	$grid._createTrColumn = function(col,$row,row,isBody)
	{
		if(col.edit && col.editor)
		{
			this._createEditColumn(col,col.editor,$row,row,isBody);
		}else
		{
			this._createPlainColumn(col,$row,row,isBody);
		}	
	};
	// 创建普通文本列
	$grid._createPlainColumn = function(col,$row,row,isBody)
	{
		var $td = this._createTdElement(col,$row,row,isBody);
		if(isBody)
		{	
			// 如果是行号列
			if(col.isRowNumberCol)
			{
				$td.html(this.rowIndex + 1);
			}else
			{
				$td.html(col.formatter 
					? col.formatter(row[col.field],col,row,$row.attr("row-id"),this.rowIndex) 
					: row[col.field]);
			}	
		}else
		{
			$td.html(row);
		}	
	};
	// 创建编辑列
	$grid._createEditColumn = function(col,editOpt,$row,row,isBody)
	{
		var $td = this._createTdElement(col,$row,isBody);
		// 覆盖列的编辑类型
		if(row && row.overrideOptions && row.overrideOptions.colEditOptions)
		{
			editOpt = $.extend(true,{},editOpt,row.overrideOptions.colEditOptions[col.field] || {});
		}
		$td.attr("edit-type",editOpt.type);
		switch(editOpt.type)
		{
			case 'text' :
				this._createPalinText($td,col.field ? row[col.field] : '');
			break;
			case 'textarea' : 
				this._createTextArea($td,col.field ? row[col.field] : '');
				break;
			case 'select' : 
				this._createSelect($td,row,editOpt,isBody);
				break;
			case 'checkbox' :
				this._createCheckboxOrRadio($td,col.field ? row[col.field] : '',editOpt,isBody);
				break;
			case 'radio' : 
				this._createCheckboxOrRadio($td,col.field ? row[col.field] : '',editOpt,isBody);
				break;
			case 'date' :
				this._createDateBox($td,col.field ? row[col.field] : '',editOpt);
				break;
		}
		// 如果是隐藏列
		if(col.hidden)
		{
			$td.children("input").attr("type","hidden");
		}
		// 如果需要name属性
		if(col.name)
		{
			$td.children("input,textarea,select").attr("name",col.name);
			$td.find("input." + this.config.editorClass.checkboxOrRadio_input)
				.attr("name",col.name);
		}	
		if(editOpt && editOpt.onChange)
		{
			$td.children("input,textarea").bind("change",function(){
				editOpt.onChange.call(this,this.value);
			});
		}	
		// 注册此插件特有的固定列的事件（如行选择的checkbox等）
		this._registerFixedColumnEvents($td,isBody);
	};
	$grid._createTdElement = function(col,$row,isBody)
	{
		var $td = $("<td/>").addClass(this.config.tdClass)
			.attr("id",col.field ? this.config.colIndexPre 
					+ col.field : this.config.colIndexPre + (new Date().getTime()))
			.attr("field",col.field ? col.field : "")
			.addClass(col.addClass ? col.addClass : '')
			.css({'text-align': (isBody && col.align) ? col.align : ''})
			.appendTo($row);
		// 隐藏列
		if(col.hidden)
		{
			$td.css("display","none");
//			$td.css({"visibility":"hidden","width":'0px'});
		}	
		// 如果是可删除的列或者是动态列
		if(col.canDelete || col.dynamicCol)
		{
			// 如果是表格头，则添加删除列的按钮
			!isBody ? this._createDelColBtn($td) : null;
			// 为列添加动态属性
			$td.attr("dynamic-col",true);
		}
		this._setTdWidth(col,$td);
		return $td;
	};
	$grid._createDelColBtn = function($td)
	{
		var colIndex,field,$del,_this = this;
		colIndex  = $td.parent().find("td").index($td);
		$del = $('<i/ class="fa fa-remove fa-2x">').addClass(this.config.delColBtnClass)
			.attr("title",this.config.delColBtnTitle)
			.appendTo($td);
		// 绑定删除事件
		$del.on("click",function(){
			colIndex = $(this).parent().parent().find("td").index($(this).parent());
			// 如果列头是可编辑的
			if($(this).parent().attr("edit-type"))
			{
				field = _this._getEditColData($(this).parent(),$(this).parent().attr("edit-type"));
			}else
			{
				field = $(this).parent().attr("field");
			}	
			// 删除列
			_this.deleteColumn(colIndex);
			// 删除列事件回调
			if(_this.options.onDeleteColumn)
			{
				_this.options.onDeleteColumn.call(_this,colIndex,field);
			}	
		});
	};
	$grid._createPalinText = function($td,data)
	{
		$("<input/>").addClass(this.config.editorClass.text)
			.val(data).appendTo($td);
	};
	$grid._createTextArea = function($td,data)
	{
		$("<textarea/>").addClass(this.config.editorClass.textarea)
			.val(data).appendTo($td);
	};
	$grid._createSelect = function($td,row,editOpt,isBody)
	{
		var _this = this,$s,colIndex,selData,field = $td.attr("field")
			,colVal = isBody ? (field ? row[field] : null) : field;
		// 创建用于生成select2下拉插件的select标签（注：如果select标签内有一个空值的option标签，则select2标签不会自动选中第一个选项，否则会自动选择第一个选项）
		$s = $("<select><option></option></select>")
			.addClass(this.config.editorClass.select)
			.appendTo($td);
		// 列的位置
		colIndex = $td.parent().find("td").index($td);
		// 如果是新增行，则下拉数据从当前列的缓存数据中获取,否则从列的配置项中获取默认下拉数据
		selData = ($td.parent().data("isAddRow") ? 
			(this._cacheColData[colIndex] ? this._cacheColData[colIndex] 
			: (editOpt.dataSourceField ? row[editOpt.dataSourceField] : editOpt.data)) 
			: (editOpt.dataSourceField ? row[editOpt.dataSourceField] : editOpt.data));
		selData = (selData && selData.length) ? selData : [];
		// 如果有下拉数据 且 有列字段 且 行数据中列字段对应的值不为空,则设置为自动选中
		colVal = $.trim(colVal);
		if(colVal && colVal != 'null' && colVal != 'undefined')
		{
			var contain = false;
			$.each(selData,function(i,o){
				if(this.id && this.id == colVal )
				{
					this.selected = true;
					contain = true;
					return false;
				}	
			});
			// 如果当前的字段值不存在于下拉数据中，则要将当前值追加下拉数据中且默认选中
			contain ? null : selData.push({'id':colVal,'text':colVal,'selected':true});
		}	
		// 设置下拉列表值
		$s.select2($.extend({},this.config.select2Opts,_this.options.select2Options,{data:selData},
				(row.overrideOptions && row.overrideOptions.select2Options) ? row.overrideOptions.select2Options : {})); // 行数据动态覆盖select2选项
		// 注册select2值选择事件
		if(isBody)
		{
			// 缓存当前子下拉列的
			_this._cacheColSelectData(colIndex,selData);
			
			$s.on("select2:select",function(){
				editOpt.onSelect ? editOpt.onSelect.call(this,$(this).val(),
						$(this).children("option:selected").text()) : null;
			});
		}else
		{
			$s.on("select2:select",function(){
				_this._select2_onSelect(this,editOpt.onSelect);
			});
		}	
	};
	$grid._createCheckboxOrRadio = function($td,data,editOpt,isBody)
	{
		data = data + '';
		var $label = $("<label/>")
			.addClass(this.config.editorClass.checkboxOrRadio_label)
			.appendTo($td);
		var $input = $("<input/>")
			.attr("type",editOpt.type)
			.attr("checked",(String(data).toUpperCase() == 'TRUE' || String(data).toUpperCase() == 'CHECKED' 
				|| String(data).toUpperCase() == 'ON') ? true : false)
			.addClass(this.config.editorClass.checkboxOrRadio_input)
			.appendTo($label);
		// iCheck插件 
		$input.iCheck2();
	};
	$grid._createDateBox = function($td,data,editOpt)
	{
		$("<input/>").addClass(this.config.editorClass.date)
			.attr("id","datebox_" + this.rowIndex + "_" + new Date().getTime())
			.val(data)
			.appendTo($td)
			.bind("click",function(){
				// laydate插件
				laydate({elem : "#" + this.id,
					istime : true,
					format:editOpt.dateFormatter || this.config.dateFormatter});
			});
	};
	$grid._select2_onSelect = function(select,onSelect)
	{
		var key,text,field,colIndex,$td,_this = this;
		key = $(select).val();
		text = $(select).children("option:selected").text();
		field = key ? key : text;
		$td = $(select).parent("td");
		colIndex = this.ele.find("thead").find("tr").find("td").index($td);
		// 重置当前列的id,field属性值
		$td.attr("id",this.config.colIndexPre + field).attr("field",field);
		
		// 更新列配置信息
		_this.options.columns[colIndex].field = field;
		
		this.ele.find("tbody").find("tr").each(function(rowIndex,tr){
			$(this).find("td").each(function(tdIndex,td){
				if(tdIndex == colIndex)
				{
					$(this).attr("id",_this.config.colIndexPre + field).attr("field",field);
					return false;
				}	
			});
		});
		// 触发事件.参数,param1:key,param2:text,param3:字段值
		onSelect ? onSelect.call(select,key,text,colIndex,field) : null;
	};
	$grid._registerFixedColumnEvents = function($ele,isBody)
	{
		var _s = this;
		// 注册选择行事件
		if($ele && $ele.hasClass(this.config.selectBoxTdClass))
		{
			if(isBody)
			{
				$ele.find("input." + this.config.editorClass.checkboxOrRadio_input)
					.on("ifChecked",function(){
						var $tr = $(this).closest("tr." + _s.config.rowClass)
							.addClass(_s.config.selectedRowClass);
						// 如果不是多选，则需要将其他行置为未选中状态
						if(!_s.options.multiSelect)
						{	
							$tr.siblings()
							.find("td." + _s.config.selectBoxTdClass)
							.find("input." + _s.config.editorClass.checkboxOrRadio_input)
							.iCheck2('uncheck');
						}	
					})
					.on("ifUnchecked",function(){
						$(this).closest("tr." + _s.config.rowClass)
							.removeClass(_s.config.selectedRowClass);
					});
			}else
			{
				$ele.find("input." + this.config.editorClass.checkboxOrRadio_input)
					.on("ifChecked",function(){
						_s._rowSelectStatusTrigger('check');
					})
					.on("ifUnchecked",function(){
						_s._rowSelectStatusTrigger('uncheck');
					});
			}	
		}	
	}
	$grid._rowSelectStatusTrigger = function(type)
	{
		this.ele.children("tbody").find("td." + this.config.selectBoxTdClass)
			.find("input." + this.config.editorClass.checkboxOrRadio_input)
			.iCheck2(type);
	};
	// 设置列宽度
	$grid._setTdWidth = function(col,$td)
	{
		col.width ? $td.css({'width':col.width}) : null;
		col.minWidth ? ($td[0].style.minWidth = col.minWidth) : null;
		col.maxWidth ? ($td[0].style.maxWidth = col.maxWidth) : null;
	},
	// 加载表格数据
	$grid.loadData = function(data)
	{
		this._createBody(data);
	};
	// 获取表格数据
	$grid.getRows = function()
	{
		return this._getRows(this.config.rowClass);
	};
	// 获取已选中行的数据
	$grid.getSelectedRows = function()
	{
		return this._getRows(this.config.selectedRowClass);
	};
	$grid._getRows = function(rowClass)
	{
		var _s = this,rows = [];
		_s.ele.children("tbody").children("tr." + rowClass).each(function(i,tr){
			var row = {};
			// 如果不是忽略的行，才读取数据
			if(!$(this).hasClass(_s.config.ignoreRowClass))
			{
				$(this).children("td." + _s.config.tdClass).each(function(j,td){
					var o = _s._readColumnInfo($(this));
					if(o && o.field)
					{
						row[o.field] = o.value;
					}	
				});
				rows.push(row);
			}
		});
		return rows;
	}
	// 获取表格动态列名称
	$grid.getDyColNames = function()
	{
		var fvObj = this.getNewColsData();
		return fvObj ? fvObj.fields : [];
	};
	// 获取动态列的数据,JS对象形式
	$grid.getNewColsData = function()
	{
		var _s = this,fields = [],values = [];
		// 获取列
		_s.ele.children("thead").find("tr." + _s.config.rowClass).each(function(){
			$(this).children("td." + _s.config.tdClass).each(function(){
				if($(this).attr("dynamic-col") && $(this).attr("field"))
				{
					fields.push($(this).attr("field"));
				}	
			});
		});
		// 获取数据
		_s.ele.children("tbody").find("tr." + _s.config.rowClass).each(function(i,tr){
			if(!$(this).hasClass(_s.config.ignoreRowClass))
			{
				var colValues;
				$(this).children("td." + _s.config.tdClass).each(function(j,td){
					// 如果是动态列，则进行处理
					if($(this).attr('dynamic-col'))
					{
						colValues = colValues ? colValues : [];
						var o = _s._readColumnInfo($(this));
						if(o && o.field)
						{
							colValues.push(o.value ? o.value : 'null');
						}	
					}	
				});
				colValues ? values.push(colValues) : null;
			}
		});
		return {'fields':fields,'values':values};
	};
	// 获取动态列的数据，字符串形式,param:splite,分隔符
	$grid.getNewColsDataStr = function(split)
	{
		var _s = this,fieldStr = '',valueStr = '',
		split = split ? split : '@',fv = _s.getNewColsData();
		if(fv.fields && fv.fields.length)
		{
			$.each(fv.fields,function(){
				fieldStr += this + split;
			});
			fieldStr = fieldStr ? fieldStr.substring(0,fieldStr.lastIndexOf(split)) : '';
		}	
		if(fv.values && fv.values.length)
		{
			$.each(fv.values,function(){
				$.each(this,function(){
					valueStr += this + split;
				});
			});
			valueStr = valueStr ? valueStr.substring(0,valueStr.lastIndexOf(split)) : '';
		}	
		return {'fieldStr':fieldStr,'valueStr':valueStr};
	};
	
	// 获取动态变换
	$grid.getVariationField = function(split)
	{
		var _s = this;
		var data=[];
		fv = _s.getNewColsData();
		if(fv.fields && fv.fields.length)
		{
			$.each(fv.fields,function(){
				data.push({"id":this,"text":this});
			});
		}	
		
		
		return data;
	};
	$grid._readColumnInfo = function($td)
	{
		var _s = this,editType,field,val;
		editType = $td.attr("edit-type");
		field = $td.attr("field");
		if(!editType)
		{
			val = $td.text();
		}else
		{
			val = _s._getEditColData($td,editType);
		}	
		return {'field':field,'value':val};
	};
	$grid._getEditColData = function($td,editType)
	{
		var val = '';
		switch(editType)
		{
			case 'text':
				val = $td.children("input").val();
				break;
			case 'textarea':
				val = $td.children("textarea").val();
				break;
			case 'select' : 
				val = $td.children("select").val();
				break;
			case 'checkbox' : 
				val = $td.find('input.' + this.config.editorClass.checkboxOrRadio_input).val();
				break;
			case 'radio' : 
				val = $td.find('input.' + this.config.editorClass.checkboxOrRadio_input).val();
				break;
			default : 
				val = $td.children("input").val();
				break;
		}
		return val;
	};
	$grid._theadStatusListener = function()
	{
		// 当配置了数据行没有时自动隐藏头部的参数时
		if(this.options.hideHeadWhenEmpty && !this.ele.find("tbody").children("tr").length)
		{
			this.ele.find("thead").addClass("hidden");
			this.options.onTableHide.call(this);
		}else
		{
			this.ele.find("thead").removeClass("hidden");
			this.options.onTableShow.call(this);
		}	
	};
	$grid._rowNumberListener = function()
	{
		var _s = this;
		this.ele.find("tbody").children("tr." + this.config.rowClass).each(function(index){
//			$(this).attr("id",_s.config.rowIndexPre + index);
			$(this).children("td." + _s.config.rowNumberTdClass).html(index + 1);
		});
	};
	// 添加行
	$grid.addRow = function(row)
	{
		this._createRow(this.ele.find("tbody"),row ? row : {},true);
		this._theadStatusListener();
		this._rowNumberListener();
	};
	// 判断表格否已经存在了
	$grid.isCreated = function()
	{
		if(!this.ele)
		{
			return false;
		}	
		return this.ele.data("created");
	};
	$grid.isHidden = function()
	{
		return this.ele.find("thead").hasClass('hidden');
	};
	// 删除行(参数行ID)
	$grid.deleteRow = function(rowId)
	{
		if(typeof rowId == 'undefined')
		{
			return false;
		}	
		this.ele.find("tbody")
			.children("tr#" + this.config.rowIndexPre + rowId).remove();
		this._theadStatusListener();
		this._rowNumberListener();
		return true;
	};
	// 添加列
	$grid.addColumn = function(column,row)
	{
		if(!column || typeof column != 'object' )
		{
			return false;
		}	
		var _this = this;
		column.dynamicCol = true;
		// 创建表头列
		this._createThColumn(column,this.ele.find("thead").find("tr"),column.text,true);
		// 创建表体列
		this.ele.find("tbody").find("tr").each(function(index,tr){
			_this._createTrColumn(column,$(this),row ? row : {},true);
			$(this).children("td:last-child").attr("isAdd",true);
		});
		// 更新当前列配置信息
		this.options.columns.push(column);
		return true;
	};
	// 删除列
	$grid.deleteColumn = function(colIndex)
	{
		if(typeof colIndex == 'undefined')
		{
			return false;
		}	
		// 删除表头
		this.ele.find("thead").find("tr").find("td").each(function(tdIndex){
			if(tdIndex == colIndex)
			{
				$(this).remove();
				return;
			}	
		});
		// 删除表体
		this.ele.find("tbody").find("tr").each(function(){
			$(this).find("td").each(function(tdIndex){
				if(tdIndex == colIndex)
				{
					$(this).remove();
					return;
				}
			});
		});
		// 更新列信息,从列信息中删除
		this.options.columns.splice(colIndex,1);
		// 清除列缓存
		this._cacheColData[colIndex] = null;
		return true;
	};
	// 设置所有下拉选项值
	$grid.setAllSelectOptions = function(data,colIndex,field)
	{
		var _this = this;
		this.ele.find("tbody").children("tr").each(function(rowIndex){
			$(this).find("td").each(function(tdIndex){
				if(colIndex == tdIndex)
				{
					var oldId = $(this).children("select").val(),
						oldText = $(this).find("option:selected").text();
					// 清空select标签的内容，然后再新增一个空的option标签(注:这样是为了生成不自动选择第一个选项的select2下拉插件)
					var $select = $(this).children("select").empty();
					$("<option/>").val(oldId).text(oldText).appendTo($select);
//					$select.append($("<option/>"));
					$select.select2($.extend({},_this.config.select2Opts,_this.options.select2Options,{data:data}));
					// 缓存当前列的下拉数据	
					_this._cacheColSelectData(colIndex,data);
					return;	
				}
			});
		});
	};
	// 设置标题中所有下拉类型的下拉选项
	$grid.setAllTitleSelectOptions = function(data)
	{
		var _this = this;
		this.ele.children("thead").children("tr").each(function(){
			$(this).children("td").each(function(){
				if($(this).attr("dynamic-col") && $(this).attr("edit-type") == 'select')
				{
					var _id = $(this).children("select").val(),
						_text = $(this).children("select").children("option:selected").text();
						_options = $.extend({},_this.config.select2Opts,_this.options.select2Options,{data:data});
					var $select = $(this).children("select").empty()
						.append($("<option/>").val(_id).text(_text))
						.select2(_options);
				}	
			});
		});
	};
	// 将忽视的行重置为可用状态(参数行ID)
	$grid.resetIgnoreRow = function(rowId)
	{
		if(typeof rowId == 'undefined')
		{	
			this.ele.find("tbody").children("tr." + this.config.rowClass)
				.removeClass(this.config.ignoreRowClass);
		}else
		{
			this.ele.find("tbody").children("tr#" + this.config.rowIndexPre + rowId)
				.removeClass(this.config.ignoreRowClass);
		}	
	};
	// 将指定的行设置为忽视的行(参数行ID)
	$grid.setIgnoreRow = function(rowId)
	{
		if(typeof rowId == 'undefined')
		{
			this.ele.find("tbody").children("tr." + this.config.rowClass)
				.addClass(this.config.ignoreRowClass);
		}else
		{	
			this.ele.find("tbody").children("tr#" + this.config.rowIndexPre + rowId)
				.addClass(this.config.ignoreRowClass);
		}
	};
	// 判断当前行是否是忽略行(参数行ID)
	$grid.isIgnoreRow = function(rowId)
	{
		return this.ele.find("tbody").children("tr#" + this.config.rowIndexPre + rowId)
			.hasClass(this.config.ignoreRowClass);
	};
	// 缓存当前列的下拉值
	$grid._cacheColSelectData = function(colIndex,data)
	{
		this._cacheColData = this._cacheColData ? this._cacheColData : {};
		if(data && data.length)
		{
			$.each(data,function(i,o){
				this.selected = false;
			});
		}	
		this._cacheColData[colIndex] = data;
	};
	// 批量更新所有行的某个列的信息
	$grid.batchUpdateColumn = function(field,val)
	{
		if(!$.trim(field))
		{
			return false;
		}	
		var _this = this;
		this.ele.children("tbody").children("tr." + _this.config.rowClass).each(function(){
			$(this).children("td." + _this.config.tdClass).each(function(){
				if($(this).attr("field") == field)
				{
					_this._setColumnInfo($(this),val);
				}	
			});
		});
		return true;
	};
	// 单独更新某一行的某一列的信息
	$grid.updateColumn = function(field,val,rowIndex)
	{
		if(!$.trim(field))
		{
			return false;
		}
		var _this = this;
		this.ele.children("tbody").children("tr." + _this.config.rowClass).each(function(index,tr){
			if(index == rowIndex)
			{
				_this._setColumnInfo($(this).children('td[id="' +_this.config.colIndexPre + field + '"]'),val);
			}	
		});
	};
	$grid._setColumnInfo = function($td,val)
	{
		if(!$td || !$td.length)
		{
			return false;
		}	
		var _this = this;
		$.each($td,function(){
			switch($(this).attr("edit-type"))
			{
				case 'text' :
					$(this).children("input").val(val);
					break;
				case 'textarea' : 
					$(this).children("textarea").val(val);
					break;
				case 'select' : 
					$(this).children("select").val(val).trigger("change");
					break;
				case 'checkbox' :
					$(this).find('input.' + _this.config.editorClass.checkboxOrRadio_input).iCheck2(String(val) == 'true' ? 'check' : 'uncheck' );
					break;
				case 'radio' :
					$(this).find('input.' + _this.config.editorClass.checkboxOrRadio_input).iCheck2(String(val) == 'true' ? 'check' : 'uncheck' );
					break;
				default :
					$(this).html(val);
					break;
			}
		});
		
	};
	// 将插件扩展到jQuery
	$.fn.myGrid = function(args)
	{
		if(typeof args == 'string' 
			&& args.indexOf('_') != 0
			&& $grid[args] 
			&& typeof $grid[args] == 'function')
		{
			var THIS = this.data("This");
			if(!THIS)
			{
				return;
			}	
			return $grid[args].apply(THIS,Array.prototype.slice.call(arguments, 1));
		}else if(typeof args == 'object') 
		{
			return new $g(this,args);
		}else
		{
			$.error("MyGrid not support the arguments:" + args);
		}	
	};
	
})(window,jQuery);

