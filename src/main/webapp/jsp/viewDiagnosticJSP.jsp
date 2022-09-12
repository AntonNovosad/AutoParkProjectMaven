<%@ page import="java.util.List" %>
<%@ page import="by.devincubator.dto.VehicleDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Диагностика машин</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">На главную</a>
        <%
            List<Long> orderList = (List<Long>) request.getAttribute("orders");
            List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");
            String isWork;
            String isRepaired = "да";
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
                <th>Бак</th>
                <th>Была исправна</th>
                <th>Починена</th>
            </tr>
            <%
                for (VehicleDto vehicleDto : dtoList) {
                    if (orderList.contains(vehicleDto.getId())) {
                        isWork = "нет";
                    } else {
                        isWork = "да";
                    }
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
                <td><%=isWork%>
                </td>
                <td><%=isRepaired%>
                </td>
            </tr>
            <%}%>
        </table>
        <br/>
        <hr/>
        <br/>
    </div>
</div>
</body>
</html>
