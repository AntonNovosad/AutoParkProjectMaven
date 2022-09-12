<%@ page import="by.devincubator.dto.VehicleDto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчет</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">На главную</a>
        <%
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            double sumTax = 0;
            double sumIncome = 0;
            double sumProfit = 0;
        %>
        <br/>
        <br/>
        <hr/>
        <br/>
        <table>
            <caption>Машины</caption>
            <tr>
                <th>Тип</th>
                <th>Модель</th>
                <th>Номер</th>
                <th>Масса</th>
                <th>Дата выпуска</th>
                <th>Цвет</th>
                <th>Модель двигателя</th>
                <th>Пробег</th>
                <th>Доход с аренд</th>
                <th>Налог</th>
                <th>Итог</th>
            </tr>
            <%
                for (VehicleDto vehicleDto : dtoList) {
                    sumTax += vehicleDto.getTax();
                    sumIncome += vehicleDto.getIncome();
                    double profit = vehicleDto.getIncome() - vehicleDto.getTax();
                    sumProfit += profit;
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
                <td><%=vehicleDto.getIncome()%>
                </td>
                <td><%=vehicleDto.getTax()%>
                </td>
                <td><%=profit%>
                </td>
            </tr>
            <%}%>
        </table>
        <p> Средний налог за месяц: <strong>
            <%=sumTax / dtoList.size()%>
        </strong></p>
        <p> Средний доход за месяц: <strong>
            <%=sumIncome / dtoList.size()%>
        </strong></p>
        <p> Суммарная прибыль автопарка: <strong>
            <%=sumProfit%>
        </strong></p>
        <br/>
        <hr/>
        <br/>
    </div>
</div>
</body>
</html>
