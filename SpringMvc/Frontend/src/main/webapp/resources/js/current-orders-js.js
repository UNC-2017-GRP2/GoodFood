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
            //alert("" + latitude + " " + longitude + " " + obj.getAddressLine());
        }
    });
}

/*var map = null;
var pointsAll;
var firstSequence = [];
var routes = [];
var matrixRoutes = null;
var routeCount = 0;
var routeInterval = null;

function createRoute(points) {
    pointsAll = points;
    map = new ymaps.Map("map", {
        center: [51.6720400, 39.1843000],
        zoom: 12
    });
    ymaps.geolocation.get({
        provider: 'browser',
        mapStateAutoApply: true
    }).then(function (result) {
        var yourPoint = result.geoObjects.position;
        pointsAll.unshift(yourPoint);
        for(var i = 0; i < pointsAll.length; i++){
            firstSequence.push(i);
        }
        getRoute(pointsAll);
        /!*firstPoints = points;
        for(var i = 0; i < points.length; i++){
            pointSequence.push(i);
            addressAll.push("");
        }
        getRouteAddress(pointsAll);
        *!/
    }, function (error) {
        $.notify(getErrorString('data_error'), "error");
    });
}

function getRoute(points) {
    var count = points.length;
    routeCount = getRouteCount(count - 1);
    matrixRoutes = matrixArray(count);
    for(var i = 0; i < count - 1; i++)
        for (var j = i + 1; j < count; j++)
            pushPoint(points, i, j);
    routeInterval = setInterval(checkRoutes, 100);
}

function pushPoint(points, i, j) {
    ymaps.route([
        points[i],
        points[j]
    ],{
        avoidTrafficJams: true
    }).then(function (route){
        var path = route.getPaths().get(0);
        routes.push({time: path.getJamsTime(), path: path, point: {i: i, j: j}});
    });
}

function checkRoutes() {
    if (routes.length !== routeCount) return;
    clearInterval(routeInterval);
    $.each(routes, function (i, v) {
        matrixRoutes[v.point.i][v.point.j] = v.time;
        matrixRoutes[v.point.j][v.point.i] = v.time;
    });
    console.log("////////////time matrix");
    console.log(matrixRoutes);
    console.log("/////////");
    /!*получили матрицу с временем*!/
}

function getRouteCount(number) {
    if (number > 1) return number + getRouteCount(number - 1);
    return 1;
}

function matrixArray(size){
    var arr = [];
    for(var i = 0; i < size; i++){
        arr[i] = [];
        for(var j = 0; j < size; j++){
            if (i === j){
                arr[i][j] = null;
            }else{
                arr[i][j] = 0;
            }
        }
    }
    return arr;
}*/

//-------------------ROUTE------------------------------------------------------------------------------------
var mapForPoint = null;
var marker = null;
var coords;
var yourPoint = null;
var map = null;
var pointsAll;
var addressAll = [];

function setPoint() {
    $('#set-point-modal').modal('show');
    mapForPoint = new ymaps.Map("map-for-set-point", {
        center: [51.6720400, 39.1843000],
        zoom: 12
    });

    mapForPoint.events.add('click', function (e) {
        coords = e.get('coords');
        //alert(coords);
        marker.geometry.setCoordinates(coords);
        mapForPoint.geoObjects.add(marker);
    });
    marker = new ymaps
        .Placemark([55.753564, 37.621085], { balloonContent: null },
            {
                preset: 'islands#greenDotIconWithCaption',
                draggable: true
            });

    marker.events
        .add("dragend", function (event) {
            coords = self.marker.geometry.getCoordinates();
            marker.geometry.setCoordinates(coords);
            mapForPoint.geoObjects.add(marker);
            //alert(coords);
        });
}

function createRoute(points) {
    pointsAll = points;
    map = new ymaps.Map("map", {
        center: [51.6720400, 39.1843000],
        zoom: 12
    });
    ymaps.geolocation.get({
        provider: 'browser',
        mapStateAutoApply: true
    }).then(function (result) {
        yourPoint = result.geoObjects.position;
        //alert(yourPoint);
        points.unshift(yourPoint);
        firstPoints = points;
        for(var i = 0; i < points.length; i++){
            pointSequence.push(i);
            addressAll.push("");
        }
        //finalRoute(firstPoints);
        getRouteAddress(pointsAll);
        getRoute(points);
    }, function (error) {
        $.notify(getNotificationString('get_point'), "info");
        //alert(getErrorString('data_error'));

        setPoint();

    });
}

function createRouteWithoutGeolocation() {
    $('#set-point-modal').modal('hide');
    //alert(coords);
    if (coords != null){
        pointsAll.unshift(coords);
        firstPoints = pointsAll;
        for (var i = 0; i < pointsAll.length; i++) {
            pointSequence.push(i);
            addressAll.push("");
            //alert(i);
        }
        //finalRoute(firstPoints);
        getRouteAddress(pointsAll);
        getRoute(pointsAll);
    }
}


function getRouteAddress(points){
    for (var i = 0; i < points.length; i++){
        //alert("point " + points[i] + "  i = " + i);
        getAddress(points[i], i);
       // alert(addressAll[i]);
    }
}

function getAddress(point, i){
    ymaps.geocode(point).then(function(res){
        if (res.geoObjects.get(0) != null){
            var obj = res.geoObjects.get(0);
            console.log("point: " + i + "  address: " + obj.getAddressLine());
            addressAll[i] = obj.getAddressLine();
            //alert("idx = " + i + "  address: " + addressAll[i]);
            //alert("addre in getAddress " + obj.getAddressLine());
            //return obj.getAddressLine();
            //alert("" + latitude + " " + longitude + " " + obj.getAddressLine());
        }
    });
}

var routes = [];
var matrixRoutes = null;
var bottom = 0;
var pointSequence = [];
var resultPointsSequence = [];
var firstPoints = null;
var routeCount = 0;
var routeInterval = null;
var resultPoints = [];
var resultPointsForRoute = [];
function getRouteCount(number) {
    if (number > 1) return number + getRouteCount(number - 1);
    return 1;
}

function matrixArray(size){
    var arr = [];
    for(var i = 0; i < size; i++){
        arr[i] = [];
        for(var j = 0; j < size; j++){
            if (i === j){
                arr[i][j] = null;
            }else{
                arr[i][j] = 0;
            }
        }
    }
    return arr;
}

function getRoute(points) {
    var count = points.length;
    routeCount = getRouteCount(count - 1);
    matrixRoutes = matrixArray(count);
    for(var i = 0; i < count - 1; i++)
        for (var j = i + 1; j < count; j++)
            pushPoint(points, i, j);
    routeInterval = setInterval(checkRoutes, 100);
}

function pushPoint(points, i, j) {
    ymaps.route([
        points[i],
        points[j]
    ],{
        avoidTrafficJams: true
    }).then(function (route){
        var path = route.getPaths().get(0);
        routes.push({time: path.getJamsTime(), path: path, point: {i: i, j: j}});
    });
}

function checkRoutes() {
    if (routes.length !== routeCount) return;
    clearInterval(routeInterval);
    //console.log(routes);
    $.each(routes, function (i, v) {
        matrixRoutes[v.point.i][v.point.j] = v.time;
        matrixRoutes[v.point.j][v.point.i] = v.time;
    });
    console.log("////////////time matrix");
    console.log(matrixRoutes);
    console.log("/////////");
    /*получили матрицу с временем*/
    var newMatrix = getMatrixWithIndexes(matrixRoutes);
    //var testM = [[null, 90,80,40,100],[60,null,40,50,70],[50,30,null,60,20],[10,70,20,null,50],[20,40,50,20,null]];
    //testM = getMatrixWithIndexes(testM);


    //tsp(newMatrix);    // ЭТО ЧЕРЕЗ АЛГОРИТМ, ОН НЕ ОСОБО

// ЭТО ПО МАТРИЦЕ ИДЕМ ПОСТОЯННО К КОРОТКИМ ТОЧКАМ
    getSeq(matrixRoutes , 0);
    //alert(seq);
    pointsForRouteFromSequence(seq);
    finalRoute(resultPointsForRoute);
}




var seq = [];

function getSeq(matrix, idx){
   // alert(idx);
    seq.push(idx);
    var min = Number.MAX_VALUE;
    var idxMin = null;
    for (var i = 0; i < matrix.length; i++){
        if (idx !== i){
            if (matrix[idx][i] != null){
                if (matrix[idx][i] < min){
                    min = matrix[idx][i];
                    idxMin = i;
                }
            }
        }
    }
    if (idxMin != null){
        for (var j = 0; j < matrix.length; j++){
            matrix[j][idx] = null;
            matrix[j][idxMin] = null;
        }
        getSeq(matrix, idxMin);
    }
}





function getMatrixWithIndexes(matrix){
    var result = [];
    var firstRow = [];
    firstRow.push(null);
    var i;
    for(i = 0; i < matrix.length; i++){
        firstRow.push(i);
    }
    result.push(firstRow);
    for (i = 0; i < matrix.length; i++){
        var row = matrix[i].slice();
        row.unshift(i);
        result.push(row);
    }
    return result;
}

function tsp(matrixRoutes){
    getReduceMatrix(matrixRoutes);
    getPoint(matrixRoutes);
}

function getReduceMatrix(matrixRoutes){
    var i,j;
    var minInRows = [];
    var minInColumns = [];
    for(i = 0; i < matrixRoutes.length; i++){
        minInRows.push(0);
        minInColumns.push(0);
    }
    for (i = 1; i < matrixRoutes.length; i++){
        var minInRow = Number.MAX_VALUE;
        for(j = 1; j < matrixRoutes[i].length; j++){
            if (matrixRoutes[i][j] != null){
                if (matrixRoutes[i][j] < minInRow){
                    minInRow = matrixRoutes[i][j];
                }
            }
        }
        minInRows[i] = minInRow;
        bottom += minInRow;
    }
    for (i = 1; i < matrixRoutes.length; i++){
        for (j = 1; j < matrixRoutes[i].length; j++){
            if (matrixRoutes[i][j] != null){
                matrixRoutes[i][j] = Number((matrixRoutes[i][j] - minInRows[i]).toFixed(2));
            }
        }
    }
    for (i = 1; i < matrixRoutes.length; i++){
        var minInColumn = Number.MAX_VALUE;
        for (j = 1; j < matrixRoutes.length; j++){
            if (matrixRoutes[j][i] != null){
                if (matrixRoutes[j][i] < minInColumn){
                    minInColumn = matrixRoutes[j][i];
                }
            }
        }
        minInColumns[i] = minInColumn;
        bottom += minInColumn;
    }
    for (i = 1; i < matrixRoutes.length; i++){
        for (j = 1; j < matrixRoutes.length; j++){
            if (matrixRoutes[j][i] != null){
                matrixRoutes[j][i] = Number((matrixRoutes[j][i] - minInColumns[i]).toFixed(2));
            }
        }
    }

    bottom = Number(bottom.toFixed(2));
}

function getPoint(matrix){
    //alert("123");
    if (matrix.length !== 0){
        var i, j, k, m;
        var minInRows = [];
        var minInColumns = [];
        for(i = 0; i < matrix.length; i++){
            minInRows.push(0);
            minInColumns.push(0);
        }
        for (i = 1; i < matrix.length; i++){
            for(j = 1; j < matrix[i].length; j++){
                if (matrix[i][j] === 0){
                    var tempRow = matrix[i].slice();
                    tempRow[j] = null;
                    var minInRow = Number.MAX_VALUE;
                    for (k = 1; k < tempRow.length; k++){
                        if (tempRow[k] != null){
                            if (tempRow[k] < minInRow){
                                minInRow = tempRow[k];
                            }
                        }
                    }
                    minInRows[i] = minInRow;
                    break;
                }
            }
        }
        for (i = 1; i < matrix.length; i++){
            for(j = 1; j < matrix.length; j++){
                if (matrix[j][i] === 0){
                    var tempColumn = [];
                    for (m = 0; m < matrix.length; m++){
                        tempColumn.push(matrix[m][i]);
                    }
                    tempColumn[j] = null;
                    var minInColumn = Number.MAX_VALUE;
                    for (k = 1; k < tempColumn.length; k++){
                        if (tempColumn[k] != null){
                            if (tempColumn[k] < minInColumn){
                                minInColumn = tempColumn[k];
                            }
                        }
                    }
                    minInColumns[i] = minInColumn;
                    break;
                }
            }
        }
        var maxSum = 0;
        var idxI = 0;
        var idxJ = 0;
        for (i = 1; i < matrix.length; i++){
            for(j = 1; j < matrix[i].length; j++){
                if (matrix[i][j] === 0){
                    var sum = minInRows[i] + minInColumns[j];
                    if (sum > maxSum){
                        maxSum = sum;
                        idxI = i;
                        idxJ = j;
                    }
                }
            }
        }
        var bottomCurSet = bottom + maxSum;
        matrix[idxI][idxJ] = null;
        getReduceMatrixForSet(matrix);
        var realI = matrix[idxI][0];
        var realJ = matrix[0][idxJ];
        for(i = 1; i < matrix.length; i++){
            if (matrix[i][0] === realJ){
                for(j = 1; j < matrix[i].length; j++){
                    if(matrix[0][j] === realI){
                        matrix[i][j] = null;
                    }
                }
            }
        }
        for (i = 0; i < matrix.length; i++){
            matrix[i].splice(idxJ, 1);
        }
        matrix.splice(idxI, 1);
        getReduceMatrixForSet(matrix);
        if (bottom + newBottom <= bottomCurSet){
            bottom += newBottom;
            var point = [realI, realJ];
            resultPoints.push(point);
            //alert("123");
            getPoint(matrix);
        }else{
            //alert("no");
            //console.log(resultPoints);
        }
    }else{
        //console.log(resultPoints);
        removePoint(resultPoints);
        //console.log(resultPoints);
        getPointSequence(resultPoints, 0);
        //console.log(firstPoints);
        pointsForRouteFromSequence(resultPointsSequence);
        //alert("fdvdfvfd");
        console.log("///first point sequence");
        console.log(pointSequence);
        console.log("//////");
        console.log("//////result point sequence");
        console.log(resultPointsSequence);
        console.log("//////");
        finalRoute(resultPointsForRoute);
    }
}

var newBottom = 0;
function getReduceMatrixForSet(matrix){
    newBottom = 0;
    var i,j;
    var minInRows = [];
    var minInColumns = [];
    for(i = 0; i < matrix.length; i++){
        minInRows.push(0);
        minInColumns.push(0);
    }

    for (i = 1; i < matrix.length; i++){
        var minInRow = Number.MAX_VALUE;
        for(j = 1; j < matrix[i].length; j++){
            if (matrix[i][j] != null){
                if (matrix[i][j] < minInRow){
                    minInRow = matrix[i][j];
                }
            }
        }
        minInRows[i] = minInRow;
        newBottom += minInRow;
    }

    for (i = 1; i < matrix.length; i++){
        for (j = 1; j < matrix[i].length; j++){
            if (matrix[i][j] != null){
                matrix[i][j] = Number((matrix[i][j] - minInRows[i]).toFixed(2));
            }
        }
    }

    for (i = 1; i < matrix.length; i++){
        var minInColumn = Number.MAX_VALUE;
        for (j = 1; j < matrix.length; j++){
            if (matrix[j][i] != null){
                if (matrix[j][i] < minInColumn){
                    minInColumn = matrix[j][i];
                }
            }
        }
        minInColumns[i] = minInColumn;
        newBottom += minInColumn;
    }

    for (i = 1; i < matrix.length; i++){
        for (j = 1; j < matrix[i].length; j++){
            if (matrix[j][i] != null){
                matrix[j][i] = Number((matrix[j][i] - minInColumns[i]).toFixed(2));
            }
        }
    }
    newBottom = Number(newBottom.toFixed(2));
}

function removePoint(points){
    for (var i = 0; i < points.length; i++){
        if (points[i][1] === 0){
            points.splice(i, 1);
            break;
        }
    }
}

function getPointSequence(points, number){
    //alert ("number = " + number);
    resultPointsSequence.push(number);
    //alert(resultPointsSequence);
    for (var i = 0; i < points.length; i++){
        if (points[i][0] === number){
            getPointSequence(points, points[i][1]);
            //break;
        }
    }
}

function pointsForRouteFromSequence(sequence){
    for (var i = 0; i < sequence.length; i++){
        resultPointsForRoute.push(firstPoints[sequence[i]]);
    }
}

function getRouteTime(pointSequence){
    for (var j = 0; j < addressAll.length; j++){

    }
    //console.log(pointSequence);
    var time = 0;
    var curTime;
    for (var i = 0; i < pointSequence.length - 1; i++){
        curTime = matrixRoutes[pointSequence[i]][pointSequence[i + 1]];
        time += curTime;
        console.log("route:" + pointSequence[i] + "->" + pointSequence[i + 1] + " time:" + curTime);
        //alert(addressAll[i] + "  " + addressAll[i + 1]);
        //console.log("route:" + addressAll[i] + "->" + addressAll[i + 1] + " time:" + curTime);
    }
    /*time += matrixRoutes[pointSequence[pointSequence.length - 1]][pointSequence[0]];
    console.log("route:" + pointSequence[pointSequence.length - 1] + "->" + pointSequence[0] + " time:" + time);*/
    console.log("Time total:" + time);
    //return time;
}


/*function createPointsArray(matrixRoutes, idx){
    resultPointsSequence.push(idx);
    visited[idx] = true;
    var i = 0, min = Number.MAX_VALUE, nextIdx = null;
    for (i = 0; i < matrixRoutes[idx].length; i++){
        //alert(matrixRoutes[idx]);
        //alert(matrixRoutes[idx][i]);
        if (matrixRoutes[idx][i] != null && !visited[i]){
            if (matrixRoutes[idx][i] < min){
                min = matrixRoutes[idx][i];
                nextIdx = i;
            }
        }
    }
    if (nextIdx != null){
        createPointsArray(matrixRoutes, nextIdx);
    }else{
        var finalPoints = [];
        alert(resultPointsSequence);
        for (var l = 0; l < resultPointsSequence.length; l++){
            finalPoints.push(pointsAll[l]);
        }
        finalRoute(finalPoints);
    }
}*/



function finalRoute(finPoints) {
    ymaps.route(
        finPoints, {
            mapStateAutoApply: true,
            boundsAutoApply: true,
            avoidTrafficJams: true
        }).then(function (route) {
        route.getPaths().options.set({
            strokeColor: '0000ffff',
            opacity: 0.9
        });
        // добавляем маршрут на карту
        map.geoObjects.add(route);
    });

    /*var route = new ymaps.multiRouter.MultiRoute({
        referencePoints: finPoints
    }, {
        editorDrawOver: false,
        wayPointDraggable: true,
        viaPointDraggable: true,
        // Задаем собственное оформление линий мультимаршрута.
        routeStrokeColor: "000088",
        routeActiveStrokeColor: "ff0000",
        pinIconFillColor: "ff0000",
        boundsAutoApply: true,
        zoomMargin: 30,
        activeRouteAutoSelection: true
    });
    map.geoObjects.add(route);*/
    getRouteTime(resultPointsSequence);
}
//------------------------------------------------------------------------------------------------------------
$(document).ready(function () {

});