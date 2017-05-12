<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- spring 标签 -->
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- jstl c标签 -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- jstl fn标签 -->
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- jstl fmt标签 -->
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- jsp 2.0默认是禁止执行el表达式的，如需执行在需要手动开启 -->
<%@ page isELIgnored="false" %>

<!-- 设置应用的jsp上下文(contextPath)属性,scope必须设置，不然用el表达式娶不到值 -->
<c:set var="contextPath" value="<%=request.getContextPath()%>" scope="session"/>
<c:set var="defaultPage" value="/pages/home/home.jsp" scope="session"/>
<!-- 应用版本号，先放在前端，解决在修改一些代码后，一些浏览器需要手动清楚缓存才能生效的问题。当前若需要更改版本，在此修改value=XX即可 -->
<c:set var="appV" value="1.3.2.1" scope="session"/>




