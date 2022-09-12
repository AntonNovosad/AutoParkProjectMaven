<%@ page import="java.util.List" %>
<%@ page import="by.devincubator.dto.VehicleDto" %>
<%@ page import="by.devincubator.dto.RentDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Информация машины</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">На главную</a>
        <a class="ml-20" href="/viewCars">Назад</a>
        <br/>
        <br/>
        <hr/>
        <br/>
        <table>
            <caption>Информация о машине</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Бак</th>
                <th>Расход</th>
                <th>Коэфф налога</th>
                <th>km на полный бак</th>
            </tr>
            <%
                List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
                int id = Integer.parseInt(request.getParameter("id"));
                VehicleDto vehicleDto = dtoList.get(id);
            %>
            <tr>
                <td><%=vehicleDto.getTypeName()%>
                </td>
                <td><%=vehicleDto.getModelName()%>
                </td>
                <td><%=vehicleDto.getRegistrationNumber()%>
                </td>
                <td><%=vehicleDto.getWeight()%>
                </td>
                <td><%=vehicleDto.getManufactureYear()%>
                </td>
                <td><%=vehicleDto.getColor()%>
                </td>
                <td><%=vehicleDto.getEngineName()%>
                </td>
                <td><%=vehicleDto.getMileage()%>
                </td>
                <td><%=vehicleDto.getTankVolume()%>
                </td>
                <td><%=vehicleDto.getPer100km()%>
                </td>
                <td><%=vehicleDto.getEngineTaxCoefficient()%>
                </td>
                <td><%=vehicleDto.getMaxKm()%>
                </td>
            </tr>
        </table>
        <p> Налог за месяц: <strong>
            <%=vehicleDto.getTax()%>
        </strong></p>
        <br/>
        <hr/>
        <br/>
        <table>
            <caption>Информация об аренде</caption>
            <tr>
                <th>Дата</th>
                <th>Стоимость</th>
            </tr>
            <%
                List<RentDto> rentDtoList = (List<RentDto>) request.getAttribute("rents");
                double profit = vehicleDto.getIncome() - vehicleDto.getTax();
                for (RentDto rentDto : rentDtoList) {
            %>
            <tr>
                <td><%=rentDto.getDate()%>
                </td>
                <td><%=rentDto.getPrice()%>
                </td>
            </tr>
            <%}%>
        </table>
        <p> Cумма: <strong>
            <%=vehicleDto.getIncome()%>
        </strong></p>
        <p> Доход: <strong>
            <%=profit%>
        </strong></p>
        </table>
    </div>
</div>
</body>
</html>
