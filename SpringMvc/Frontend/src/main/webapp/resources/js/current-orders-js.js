function getUserName(orderId, userId) {
    $.ajax({
        url: 'getUsername',
        type: 'GET',
        data: ({
            userId: userId
        }),
        dataType: "text",
        success: function (data) {
            $("#user"+orderId).text(data);
        },
        error: function () {
            alert("error");
        }
    });
}

function getAddressByCoordinates(orderId, latitude, longitude){
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function(res){
        if (res.geoObjects.get(0) != null){
            var obj = res.geoObjects.get(0);
            $("#address"+orderId).text(obj.getAddressLine());
        }
    });
}
/*
var myMap;*/

/*function createRoute(points){
/!*    var myMap = new ymaps.Map("map", {
        center: [51.6720400, 39.1843000],
        zoom: 12
    });*!/
    var points1 = [];
    points1.push(points[0]);
    points1.push(points[1]);
/!*
    var traffic = new ymaps.Traffic.Control();
    var route = new ymaps.multiRouter.Router({
        referencePoints: points1,
        // Параметры маршрутизации.
        params: {
            // Ограничение на максимальное количество маршрутов, возвращаемое маршрутизатором.
            results: 2
        },
        avoidTrafficJams: traffic.isShown()
    }, {
        // Автоматически устанавливать границы карты так, чтобы маршрут был виден целиком.
        boundsAutoApply: true
    });
    myMap.geoObjects.add(route);
*!/


    YMaps.jQuery(function () {
        var traffic = new YMaps.Traffic.Control({ showInfoSwitcher: true }, { infoLayerShown: true });

        var map = new ymaps.Map("map", {
            center: [51.6720400, 39.1843000],
            zoom: 12
        });

        traffic.show();

        var router = new YMaps.Router(
            points1,
            {
                viewAutoApply: true,
                avoidTrafficJams: traffic.isShown()
            });

        map.addOverlay(router);
        map.addControl(traffic);
        YMaps.Events.observe(router, router.Events.Success, function () {
            alert('Дистанция: '
                + router.getDistance()
                + ' Время в пути: '
                + parse_duration(router.getDuration())
            );
        });
    });

}*/
var map = null;

/*function route(points) {
    ymaps.route(
        points, {
            mapStateAutoApply: true,
            boundsAutoApply: true,
            avoidTrafficJams: true
        }).then(function (route) {
        route.getPaths().options.set({
            // в балуне выводим только информацию о времени движения с учетом пробок
            /!*balloonContentBodyLayout: ymaps.templateLayoutFactory.createClass('$[properties.humanJamsTime]'),*!/
            // можно выставить настройки графики маршруту
            balloonContentBodyLayout: 'fvfvfv',
            strokeColor: '0000ffff',
            opacity: 0.9
        });
        // добавляем маршрут на карту
        map.geoObjects.add(route);
    });
}*/

var balloonLayout = ymaps.templateLayoutFactory.createClass(
    "<div class='my-balloon'>" +
    '<a class="close" href="#">&times;</a>' +
    "<b>Маршрут {% if properties.type == 'driving' %}" +
    "на автомобиле<br/>" +
    "{% else %}" +
    "на общественном транспорте" +
    "{% endif %}</b><br />" +
    "Расстояние: " +
    "<i>{{ properties.distance.text }}</i>,<br />" +
    "Время в пути: " +
    "<i>{{ properties.duration.text }} (без учета пробок) </i>" +
    "</div>", {

        build: function () {
            this.constructor.superclass.build.call(this);
            this._$element = $('.my-balloon', this.getParentElement());
            this._$element.find('.close')
                .on('click', $.proxy(this.onCloseClick, this));
        },

        onCloseClick: function (e) {
            e.preventDefault();
            this.events.fire('userclose');
        }
    }
);

function matrixArray(rows,columns){
    var arr = [];
    for(var i = 0; i < rows; i++){
        arr[i] = [];
        for(var j = 0; j < columns; j++){
            if (i === j){
                arr[i][j] = null;
            }
        }
    }
    return arr;
}
function getArrLabelPoints(length) {
    var arr = [];
    arr[0] = 0;
    for (var i = 1; i < length; i++){
        arr[i] = null;
    }
    return arr;
}

function getArrVisitedPoints(length) {
    var arr = [];
    for (var i = 0; i < length; i++){
        arr[i] = false;
    }
    return arr;
}
function route(points) {
    var size = points.length;
    var matrixRouteTime = matrixArray(size, size);
    var isVisitedArr = getArrVisitedPoints(size);
    var labelPointArr = getArrLabelPoints(size);
    for(var i = 0; i < matrixRouteTime.length; i++){
        for (var j = 0; j < matrixRouteTime[i].length; j++){
            //if (i !== j )
        }
    }
    //alert(matrixRouteTime[0][1]);
    ymaps.route(
        points, {
            mapStateAutoApply: true,
            boundsAutoApply: true,
            avoidTrafficJams: true
        }).then(function (route) {
        route.getPaths().options.set({
            strokeColor: '0000ffff',
            opacity: 0.9
        });
        var firstPath = route.getPaths().get(0);
        for (var i = 0; i < points.length - 1; i++){

        }
        alert(firstPath.getJamsTime() + " ");
        map.geoObjects.add(route);
        // Зададим содержание иконок начальной и конечной точкам маршрута.
        // С помощью метода getWayPoints() получаем массив точек маршрута.
        // Массив транзитных точек маршрута можно получить с помощью метода getViaPoints.
        var wayPoints = route.getWayPoints(),
            lastPoint = wayPoints.getLength() - 1;
        // Задаем стиль метки - иконки будут красного цвета, и
        // их изображения будут растягиваться под контент.
        wayPoints.options.set('preset', 'islands#blueStretchyIcon');
        // Задаем контент меток в начальной и конечной точках.
        /*wayPoints.get(0).properties.set('iconContent', 'Точка отправления');
        wayPoints.get(lastPoint).properties.set('iconContent', 'Точка прибытия');*/

        // Проанализируем маршрут по сегментам.
        // Сегмент - участок маршрута, который нужно проехать до следующего
        // изменения направления движения.
        // Для того, чтобы получить сегменты маршрута, сначала необходимо получить
        // отдельно каждый путь маршрута.
        // Весь маршрут делится на два пути:
        // 1) от улицы Крылатские холмы до станции "Кунцевская";
        // 2) от станции "Кунцевская" до "Пионерская".

        /*var moveList = 'Трогаемся,</br>',
            way,
            segments;
        // Получаем массив путей.
        for (var i = 0; i < route.getPaths().getLength(); i++) {
            way = route.getPaths().get(i);
            segments = way.getSegments();
            for (var j = 0; j < segments.length; j++) {
                var street = segments[j].getStreet();
                moveList += ('Едем ' + segments[j].getHumanAction() + (street ? ' на ' + street : '') + ', проезжаем ' + segments[j].getLength() + ' м.,');
                moveList += '</br>'
            }
        }
        moveList += 'Останавливаемся.';
        // Выводим маршрутный лист.
        $('#list').append(moveList);*/
    }, function (error) {
        alert('Возникла ошибка: ' + error.message);
    });
}

/*function route(points) {
    /!*ymaps.multiRouter.MultiRoute({
        referencePoints: points}
        ).then(function (route) {
        /!*route.getPaths().options.set({
            // в балуне выводим только информацию о времени движения с учетом пробок
            /!*balloonContentBodyLayout: ymaps.templateLayoutFactory.createClass('$[properties.humanJamsTime]'),*!/
            // можно выставить настройки графики маршруту
            balloonContentBodyLayout: 'fvfvfv',
            strokeColor: '0000ffff',
            opacity: 0.9
        });*!/
        // добавляем маршрут на карту
        map.geoObjects.add(route);
    });*!/

    var balloonLayout = ymaps.templateLayoutFactory.createClass(
        "<div class='my-balloon'>" +
        '<a class="close" href="#">&times;</a>' +
        "<b>Маршрут {% if properties.type == 'driving' %}" +
        "на автомобиле<br/>" +
        "{% else %}" +
        "на общественном транспорте" +
        "{% endif %}</b><br />" +
        "Расстояние: " +
        "<i>{{ properties.distance.text }}</i>,<br />" +
        "Время в пути: " +
        "<i>{{ properties.duration.text }} (без учета пробок) </i>" +
        "</div>", {

            build: function () {
                this.constructor.superclass.build.call(this);
                this._$element = $('.my-balloon', this.getParentElement());
                this._$element.find('.close')
                    .on('click', $.proxy(this.onCloseClick, this));
            },

            onCloseClick: function (e) {
                e.preventDefault();
                this.events.fire('userclose');
            }
        }
    );
    var multiRoute = new ymaps.multiRouter.MultiRoute({
        referencePoints: points
    },
        {
            avoidTrafficJams: true,
            routeStrokeColor: "000088",
            routeActiveStrokeColor: "ff0000",
            pinIconFillColor: "ff0000",
            boundsAutoApply: true
        });
    map.geoObjects.add(multiRoute);
    var allPoints = multiRoute.getWayPoints(),
        lastPoint = points.getLength() - 1;
    alert(allPoints + " " + lastPoint);
}*/

function createRoute(points) {
    map = new ymaps.Map("map", {
        center: [51.6720400, 39.1843000],
        zoom: 12
    });

    ymaps.geolocation.get({
        provider: 'browser',
        //autoReverseGeocode: true,
        mapStateAutoApply: true
    }).then(function (result) {
        /*result.geoObjects.get(0).properties.set({
            balloonContentBody: 'Мое местоположение'
        });*/

        var yourPoint = result.geoObjects.position;
        /*var placemark = new ymaps.Placemark(yourPoint, {
            iconCaption: "It's you"
        }, {
            preset: 'islands#redDotIconWithCaption'
        });

        map.geoObjects
            .add(placemark);*/
        points.unshift(yourPoint);
        route(points);

        /*map = new ymaps.Map("map", {
            center: [51.6720400, 39.1843000],
            zoom: 12
        });
        var route = new ymaps.multiRouter.MultiRoute({
            referencePoints: [itsYourPoint, points[0]],
            // Параметры маршрутизации.
            params: {
                // Ограничение на максимальное количество маршрутов, возвращаемое маршрутизатором.
                results: 2
            }
        },{
            // Автоматически устанавливать границы карты так, чтобы маршрут был виден целиком.
            boundsAutoApply: true
        });
        myMap.geoObjects.add(route);*/

    }, function (error) {
        alert("err");
        /*myMap = new ymaps.Map("map", {
            center: [51.6720400, 39.1843000],
            zoom: 12
        });
        myMap.geoObjects.add(new ymaps.Placemark(points[1], {
            balloonContent: '<strong>' + name + '</strong>'
        }, {
            preset: 'islands#dotIcon',
            iconColor: '#735184'
        }))*/

    });
}
//------------------------- постороение маршрута-------------------------
var multiRouteModel;

/*function route() {
    multiRouteModel = new ymaps.multiRouter.MultiRouteModel([
        position, points[2]
    ], {});

/!*    var routeTypeSelector = new ymaps.control.ListBox({
        data: {
            content: 'Как добраться'
        },
        items: [
            new ymaps.control.ListBoxItem({ data: { content: "На авто" }, state: { selected: true } }),
            new ymaps.control.ListBoxItem({ data: { content: "Общественным транспортом" } }),
            new ymaps.control.ListBoxItem({ data: { content: "Пешком" } })
        ],
        options: {
            itemSelectOnClick: false
        }
    });*!/

  /!*  var autoRouteItem = routeTypeSelector.get(0),
        masstransitRouteItem = routeTypeSelector.get(1),
        pedestrianRouteItem = routeTypeSelector.get(2);

    autoRouteItem.events.add('click', function (e) { changeRoutingMode('auto', e.get('target')); });
    masstransitRouteItem.events.add('click', function (e) { changeRoutingMode('masstransit', e.get('target')); });
    pedestrianRouteItem.events.add('click', function (e) { changeRoutingMode('pedestrian', e.get('target')); });*!/

    ymaps.modules.require([
        'MultiRouteCustomView'
    ], function (MultiRouteCustomView) {
        new MultiRouteCustomView(multiRouteModel);
    });

    myMap = new ymaps.Map('Map', {
        center: [51.6720400, 39.1843000],
        zoom: 7
    }, {
        buttonMaxWidth: 300
    });

    multiRoute = new ymaps.multiRouter.MultiRoute(multiRouteModel, {});

    myMap.geoObjects.add(multiRoute);

/!*    myMap.setBounds([position, [longitude, latitude]]);
    function changeRoutingMode(routingMode, targetItem) {
        multiRouteModel.setParams({ routingMode: routingMode }, true);

        // Отменяем выбор элементов.
        autoRouteItem.deselect();
        masstransitRouteItem.deselect();
        pedestrianRouteItem.deselect();

        // Выбираем элемент и закрываем список.
        targetItem.select();
        routeTypeSelector.collapse();
    }*!/
}*/


$(document).ready(function () {

   /* ymaps.ready(init);
    function init(){
        myMap = new ymaps.Map("map", {
            center: [51.6720400, 39.1843000],
            zoom: 12
        });
    }*/

});