<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="navbar bg-light navbar-light rounded">
  <%-- <span class="navbar-brand">Cars</span> --%>
  <ul class="navbar-nav">
    <li class="nav-item">
      <a href="/controller?command=show_cars" class="nav-link">All</a>
    </li>
    <li class="nav-item">
      <a href="/controller?command=find_cars&class=premium" class="nav-link">Premium</a>
    </li>
    <li class="nav-item">
      <a href="/controller?command=find_cars&class=standart" class="nav-link">Standart</a>
    </li>
    <li class="nav-item">
      <a href="/controller?command=find_cars&class=economy" class="nav-link">Economy</a>
    </li>

  </ul>
</div>
