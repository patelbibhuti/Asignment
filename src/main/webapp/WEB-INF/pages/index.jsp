<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<form name="crawler" action="/CrawleingDemo/" method="get">
		<input type="text" id="crawlerUrl" name="crawlerUrl" />
		<input type="submit" value="Submit" />
    </form>
	<h2>Site Map</h2>

	<c:if test="${not empty crawleList}">
		<ul>
			<c:forEach var="listValue" items="${crawleList}">
				<li><a href="${listValue.url}" >${listValue.title}</a></li>
			</c:forEach>
		</ul>

	</c:if>
</body>
</html>
