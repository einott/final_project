<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Orders | einott</title>

    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/style.css">
  </head>
  <body>
      <jsp:include page = "/jsp/parts/header.jsp" />

      <main role="main" class="container">
        <div class="row">
          <div class="col-2 float-left">
            <jsp:include page = "/jsp/user/parts/profile_sidebar.jsp" />
          </div>
          <div class="col-10 float-left">
            <div class="card">
              <div class="card-header">
                <h4 class="card-title">Orders information</h4>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead>
                    <th>Username</th>
                    <th>Confirmed</th>
                    <th>Rejected</th>
                    <th>In rent</th>
                    <th>Awaits</th>
                    <th>Over</th>
                    <th>Returned</th>
                    <th>Total</th>
                  </thead>
                  <tbody>
                    <tr>
                      <td>einott</td>
                      <td>1</td>
                      <td>3</td>
                      <td>2</td>
                      <td>6</td>
                      <td>4</td>
                      <td>5</td>
                      <td>21</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="card mt-3">
              <div class="card-header">
                <h4 class="card-title">All orders</h4>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead>
                    <th>#</th>
                    <th>Car</th>
                    <th>Date start</th>
                    <th>Date end</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th></th>
                  </thead>
                  <tbody>
                    <c:forEach items="${requestScope.orders_info.userOrderList}" var = "order_item" varStatus = "loop">
                      <tr>
                        <td>${loop.index + 1}</td>
                        <td>
                          <a href="/controller?command=show_selected_car&car_id=${order_item.order.carID}">${order_item.car.brand} ${order_item.car.model}</a>
                        </td>
                        <td>${order_item.order.dateStart}</td>
                        <td>${order_item.order.dateEnd}</td>
                        <td>${order_item.order.totalPrice}$</td>
                        <td>${order_item.order.status}</td>
                        <td>
                          <form action="controller" method="post">
                            <input type="hidden" name="command" value="user_order">
                            <input type="hidden" name="order_id" value="${order_item.order.id}">
                            <button type="submit" class="btn btn-sm btn-light border">Check</button>
                          </form>
                        </td>
                      </tr>
                    </c:forEach>
                    <%-- <tr>
                      <td>1</td>
                      <td>
                        <a href="#">BMW 520i</a>
                      </td>
                      <td>05.08.2018 12:00</td>
                      <td>06.08.2018 14:00</td>
                      <td>160$</td>
                      <td><span class="text-important">Awaits</span></td>
                      <td>
                        <button type="button" class="btn btn-sm btn-light border">Check</button>
                      </td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>
                        <a href="#">Chevrolet Cobalt</a>
                      </td>
                      <td>04.08.2018 12:00</td>
                      <td>06.08.2018 12:00</td>
                      <td>90$</td>
                      <td><span class="text-success">Confirmed</span></td>
                      <td>
                        <button type="button" class="btn btn-sm btn-light border">Check</button>
                      </td>
                    </tr> --%>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </main>

      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script>window.jQuery || document.write('<script src="../../js/vendor/jquery-slim.min.js"><\/script>')</script>
      <script src="../../js/vendor/popper.min.js"></script>
      <script src="../../js/bootstrap.min.js"></script>
  </body>
</html>