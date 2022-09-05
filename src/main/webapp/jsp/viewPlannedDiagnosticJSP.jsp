<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Плановая диагностика машин</title>
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
            int max = 5;
            int min = 1;
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
                <td><%=vehicleDto.getTankVolue()%>
                </td>
                <td><%=isWork%>
                </td>
                <td><%=isRepaired%>
                </td>
            </tr>
            <%}%>
        </table>
        <p> Период: 5 минут </p>
        <p> Последняя проверка проведена <strong>
            <%=(int) Math.random() * ((max - min) + 1) + min%>
        </strong> минут назад</p>
        <br/>
        <hr/>
        <br/>
    </div>
</div>
</body>
</html>
