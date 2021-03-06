<%@ taglib prefix="paginator" uri="/WEB-INF/tlds/Paginator" %>

<%@ include file="/jsp/parts/bundle.jsp" %>

<fmt:message bundle="${loc}" key="local.cars.text.title" var="title" />
<fmt:message bundle="${loc}" key="local.cars.text.body-class" var="body_class" />
<fmt:message bundle="${loc}" key="local.cars.text.body-color" var="body_color" />
<fmt:message bundle="${loc}" key="local.cars.text.body-volume" var="body_volume" />
<fmt:message bundle="${loc}" key="local.cars.text.body-price" var="body_price" />
<fmt:message bundle="${loc}" key="local.cars.button.continue" var="button_continue" />

<!DOCTYPE html>
<html>
  <head>

    <title>${title}</title>

    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/style.css">
  </head>
  <body>

      <%@ include file="/jsp/parts/header.jsp" %>
      <main role="main" class="container">
        <div class="row">
          <div class="col-2 float-left">
            <jsp:include page = "/jsp/parts/cars_sidebar.jsp" />
          </div>
          <div class="col-10 float-left">
            <c:forEach items="${requestScope.car_list}" var = "car">
              <div class="card mb-3">
                <div class="card-header">
                  <h5 class="card-title my-1">${car.brand} ${car.model}</h5>
                </div>
                <div class="card-body">
                  <div class="row">
                    <div class="col-3 text-center">
                      <c:choose>
                        <c:when test="${car.photos.size() != 0}">
                          <img src="../img/uploads/cars/${car.photos.get(0).url}" alt="" height="97px" width="173px" class="rounded">
                        </c:when>
                        <c:otherwise>
                          <img src="../img/uploads/cars/no_car_avatar.png" alt="" height="97px" width="173px" class="rounded">
                        </c:otherwise>
                      </c:choose>
                    </div>
                    <div class="col-7">
                      <table class="table">
                        <tbody>
                          <tr>
                            <td>${body_class}:</td>
                            <td>${car.carClass}</td>
                          </tr>
                          <tr>
                            <td>${body_color}:</td>
                            <td>${car.color}</td>
                          </tr>
                          <tr>
                          <tr>
                            <td>${body_volume}:</td>
                            <td>${car.engineVolume}</td>
                          </tr>
                          <tr>
                            <td>${body_price}:</td>
                            <td>${car.price}$</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>

                </div>
                <div class="card-footer">
                  <div class="row float-right">
                    <a href="/controller?command=show_selected_car&car_id=${car.id}" class="btn btn-md btn-light border">${button_continue}</a>
                  </div>
                </div>
              </div>
            </c:forEach>

            <c:choose>
              <c:when test = "${not empty car_class}">
                <c:url value="/controller?command=show_cars&car_class=${car_class}&date_start=${date_start}&date_end=${date_end}&page=##" var="url" />
              </c:when>
              <c:otherwise>
                <c:url value="/controller?command=show_cars&date_start=${date_start}&date_end=${date_end}&page=##" var="url" />
              </c:otherwise>
            </c:choose>

            <paginator:display maxLinks="10" currentPage="${page}" totalPages="${page_count}" url="${url}" />

          </div>
        </div>
      </main>

      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script>window.jQuery || document.write('<script src="../js/vendor/jquery-slim.min.js"><\/script>')</script>
      <script src="../js/vendor/popper.min.js"></script>
      <script src="../js/bootstrap.min.js"></script>
  </body>
</html>
