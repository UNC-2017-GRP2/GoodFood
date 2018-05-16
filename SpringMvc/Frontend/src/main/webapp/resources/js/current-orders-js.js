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

//-------------------ROUTE----------------------------
var map = null;
var pointsAll;
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
        points.unshift(yourPoint);
        firstPoints = points;
        for(var i = 0; i < points.length; i++){
            pointSequence.push(i);
        }
        alert(pointSequence);
        getRoute(points);
        //route(points);
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

var routes = [];
var matrixRoutes = null;
//var reducedMatrix = null;
var firstRoute = null;
var bottom = 0;
var pointSequence = [];
var firstPoints = null;
var routeCount = 0;
var routeInterval = null;
var resultPoints = [];
function factorial(number) {
    if (number > 1) return number + factorial(number - 1);
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
    routeCount = factorial(count - 1);
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

    for (var i = 0; i < matrixRoutes.length; i++){
        visited.push(false);
    }
    console.log(matrixRoutes);
    createPointsArray(matrixRoutes, 0);
    //console.log(matrixRoutes);
    /*получили матрицу с временем*/
    //var newMatrix = getMatrixWithIndexes(matrixRoutes);
    //console.log(newMatrix);
    //var testM = [[null, 90,80,40,100],[60,null,40,50,70],[50,30,null,60,20],[10,70,20,null,50],[20,40,50,20,null]];
    //testM = getMatrixWithIndexes(testM);
    //console.log(testM);
    //tsp(newMatrix);
    //tsp(testM);
}

function getMatrixWithIndexes(matrix){
   // var matrix = matrixRoutes.slice();
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
    //var reducedMatrix = getReduceMatrix(matrixRoutes);
    alert(matrixRoutes + "start");
    getReduceMatrix(matrixRoutes);
    var firstTime = getRouteTime(pointSequence);
    getPoint(matrixRoutes);
    //console.log(reducedMatrix);
    //alert(firstTime);
}

function getReduceMatrix(matrixRoutes){
    var i,j;
    //var reducedMatrix = matrixRoutes.slice();
    //console.log("getReduceMatrixStart");
    //console.log(matrixRoutes);
    /*console.log(reducedMatrix);
    console.log("getReduceMatrixEnd");*/

    /*for (i = 0; i < matrixRoutes.length; i ++){
        reducedMatrix[i] = matrixRoutes[i].slice();
    }*/

    //console.log(reducedMatrix);
    //console.log("getReduceMatrixAfterFor");
    // console.log(reducedMatrix);
    //console.log("getReduceMatrixAfterForEnd");
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

    //console.log("min in rows " + minElemsInRows);
    //console.log("reduce row");
    //console.log(reducedMatrix);

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
        for (j = 1; j < matrixRoutes[i].length; j++){
            if (matrixRoutes[j][i] != null){
                matrixRoutes[j][i] = Number((matrixRoutes[j][i] - minInColumns[i]).toFixed(2));
            }
        }
    }

    bottom = Number(bottom.toFixed(2));
    alert(matrixRoutes + " firstReduce bottom = " + bottom);
    //console.log("min in columns " + minElemsInColumns);
    /*console.log("bottom " + bottom);
    console.log("first reduce matrix");
    console.log(matrixRoutes);*/

    /*setTimeout(function () {

    }, 5000);*/
    //return matrixRoutes;
}

function getPoint(matrix){
    alert("getPoint");
    var i, j, k, m;
    /*var reducedMatrix = matrix.slice();
    console.log("getPointStart");
    console.log(matrix);
    console.log(reducedMatrix);
    console.log("getPointEnd");*/
    /*for (i = 0; i < matrix.length; i ++){
        reducedMatrix[i] = matrix[i].slice();
    }*/
    var minInRows = [];
    var minInColumns = [];
    for(i = 0; i < matrix.length; i++){
        minInRows.push(0);
        minInColumns.push(0);
    }
    //console.log(matrix);
    for (i = 1; i < matrix.length; i++){
        for(j = 1; j < matrix[i].length; j++){
            if (matrix[i][j] === 0){
                //console.log("i = " + i + " j = " + j);
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
                //console.log(minInRow);
                //alert(tempRow + "min = " + minInRow);
                minInRows[i] = minInRow;
                break;
            }
        }
    }
    //alert(minInRows);
    for (i = 1; i < matrix.length; i++){
        for(j = 1; j < matrix.length; j++){
            if (matrix[j][i] === 0){
                var tempColumn = [];
                for (m = 1; m < matrix.length; m++){
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
                //alert(tempColumn + "min = " + minInColumn);
            }
        }
    }
    //alert(minInColumns);
    var maxSum = 0;
    var idxI = 0;
    var idxJ = 0;
    //console.log("p");
    //console.log(matrix);
    //console.log("p");
    //alert("p");

    for (i = 1; i < matrix.length; i++){
        for(j = 1; j < matrix.length; j++){
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
    alert("b " + bottom + " su " + maxSum + " cur " + bottomCurSet);
    alert(matrix);
    //console.log("b " + bottom + " su " + maxSum + " cur " + bottomCurSet);
    //console.log("i = " + idxI + " j = " + idxJ);
    getReduceMatrixForSet(matrix);
    matrix[idxJ][idxI] = null;
    var realI = matrix[idxI][0];
    var realJ = matrix[0][idxJ];
    alert("i = " + idxI + " j = " + idxJ + "realI = " + realI + " realJ = " + realJ);
    for (i = 0; i < matrix.length; i++){
        matrix[i].splice(idxJ, 1);
    }
    matrix.splice(idxI, 1);

    //console.log("start");
    getReduceMatrixForSet(matrix);

    //console.log(matrix);
    //console.log(newBottom);
    alert("b + newB = " + bottom + newBottom + "bCurSet = " + bottomCurSet);
    if (bottom + newBottom <= bottomCurSet){
        alert("yes");
        bottom += newBottom;
        alert(bottom);
        var point = [realI, realJ];
        resultPoints.push(point);
        alert(resultPoints);
        console.log(resultPoints);
        getPoint(matrix);
    }else{
        alert("no");
    }
}

var newBottom = 0;
function getReduceMatrixForSet(matrix){
    newBottom = 0;
    alert("getReducedForSet");
    var i,j;
    //var reducedMatrix = matrixRoutes.slice();
    //console.log("getReduceMatrixStart");
    //console.log(matrixRoutes);
    /*console.log(reducedMatrix);
    console.log("getReduceMatrixEnd");*/

    /*for (i = 0; i < matrixRoutes.length; i ++){
        reducedMatrix[i] = matrixRoutes[i].slice();
    }*/

    //console.log(reducedMatrix);
    //console.log("getReduceMatrixAfterFor");
    // console.log(reducedMatrix);
    //console.log("getReduceMatrixAfterForEnd");
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

    //console.log("min in rows " + minElemsInRows);
    //console.log("reduce row");
    //console.log(reducedMatrix);

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
    alert(matrix);
    alert(newBottom + " new bottom");
    /*newBottom = 0;
    var i, j;
    //var reducedMatrix = matrixRoutes.slice();
    /!*for (i = 0; i < matrixRoutes.length; i ++){
        reducedMatrix[i] = matrixRoutes[i].slice();
    }*!/
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
                matrix[j][i] = Number((matrixRoutes[j][i] - minInColumns[i]).toFixed(2));
            }
        }
    }
    newBottom = Number(newBottom.toFixed(2))*/
    /*
        for (i = 0; i < reducedMatrix.length; i++){
            for (j = 0; j < reducedMatrix[i].length; j++){
                if (reducedMatrix[j][i] != null){
                    reducedMatrix[j][i] = Number((reducedMatrix[j][i] - minElemsInColumns[i]).toFixed(2));
                }
            }
        }*/

    /*bottom = Number(bottom.toFixed(2));
    console.log("min in columns " + minElemsInColumns);
    console.log("bottom " + bottom);
    console.log("reduce column ");
    console.log(reducedMatrix);*/
}


/*function tsp(matrixRoutes){
    var reducedMatrix = getReduceMatrix(matrixRoutes);
    var firstTime = getRouteTime(pointSequence);
    getPoint(reducedMatrix);
    //console.log(reducedMatrix);
    //alert(firstTime);
}

function getReduceMatrix(matrixRoutes){
    var i = 0;
    var j = 0;
    var reducedMatrix = matrixRoutes.slice();
    //console.log("getReduceMatrixStart");
    //console.log(matrixRoutes);
    /!*console.log(reducedMatrix);
    console.log("getReduceMatrixEnd");*!/

    /!*for (i = 0; i < matrixRoutes.length; i ++){
        reducedMatrix[i] = matrixRoutes[i].slice();
    }*!/

    //console.log(reducedMatrix);
    //console.log("getReduceMatrixAfterFor");
   // console.log(reducedMatrix);
    //console.log("getReduceMatrixAfterForEnd");
    var minElemsInRows = [];
    var minElemsInColumns = [];
    for(i = 0; i < reducedMatrix.length; i++){
        minElemsInRows.push(0);
        minElemsInColumns.push(0);
    }

    for (i = 0; i < reducedMatrix.length; i++){
        //var minInRow = reducedMatrix[i][i + 1];
        var minInRow = Number.MAX_VALUE;
        for(j = 0; j < reducedMatrix[i].length; j++){
            if (reducedMatrix[i][j] != null){
                if (reducedMatrix[i][j] < minInRow){
                    minInRow = reducedMatrix[i][j];
                }
            }
        }
        minElemsInRows[i] = minInRow;
        bottom += minInRow;
    }

    for (i = 0; i < reducedMatrix.length; i++){
        for (j = 0; j < reducedMatrix[i].length; j++){
            if (reducedMatrix[i][j] != null){
                reducedMatrix[i][j] = Number((reducedMatrix[i][j] - minElemsInRows[i]).toFixed(2));
            }
        }
    }

    //console.log("min in rows " + minElemsInRows);
    //console.log("reduce row");
    //console.log(reducedMatrix);

    for (i = 0; i < reducedMatrix.length; i++){
        /!*var minInColumn = reducedMatrix[n + 1][n];*!/
        var minInColumn = Number.MAX_VALUE;
        for (j = 0; j < reducedMatrix.length; j++){
            if (reducedMatrix[j][i] != null){
                if (reducedMatrix[j][i] < minInColumn){
                    minInColumn = reducedMatrix[j][i];
                }
            }
        }
        minElemsInColumns[i] = minInColumn;
        bottom += minInColumn;
    }

    for (i = 0; i < reducedMatrix.length; i++){
        for (j = 0; j < reducedMatrix[i].length; j++){
            if (reducedMatrix[j][i] != null){
                reducedMatrix[j][i] = Number((reducedMatrix[j][i] - minElemsInColumns[i]).toFixed(2));
            }
        }
    }

    bottom = Number(bottom.toFixed(2));
    //console.log("min in columns " + minElemsInColumns);
    //console.log("bottom " + bottom);
    console.log("first reduce matrix");
    console.log(reducedMatrix);

    /!*setTimeout(function () {

    }, 5000);*!/
    return reducedMatrix;
}

function getPoint(matrix){
    var i = 0;
    var j = 0;
    var k = 0;
    var m = 0;
    var reducedMatrix = matrix.slice();
    console.log("getPointStart");
    console.log(matrix);
    console.log(reducedMatrix);
    console.log("getPointEnd");
    /!*for (i = 0; i < matrix.length; i ++){
        reducedMatrix[i] = matrix[i].slice();
    }*!/
    var minInRows = [];
    var minInColumns = [];
    for(i = 0; i < reducedMatrix.length; i++){
        minInRows.push(0);
        minInColumns.push(0);
    }

    for (i = 0; i < reducedMatrix.length; i++){
        for(j = 0; j < reducedMatrix.length; j++){
            if (reducedMatrix[i][j] === 0){
                var tempRow = reducedMatrix[i].slice();
                tempRow[j] = null;
                var minInRow = Number.MAX_VALUE;
                for (k = 0; k < tempRow.length; k++){
                    if (tempRow[k] != null && tempRow[k] !== undefined){
                        if (tempRow[k] < minInRow){
                            minInRow = tempRow[k];
                        }
                    }
                }
                minInRow[i] = minInRow;
            }
        }
    }

    for (i = 0; i < reducedMatrix.length; i++){
        for(j = 0; j < reducedMatrix.length; j++){
            if (reducedMatrix[j][i] === 0){
                var tempColumn = [];
                for (m = 0; m < reducedMatrix.length; m++){
                    tempColumn.push(reducedMatrix[m][i]);
                }
                tempColumn[j] = null;
                var minInColumn = Number.MAX_VALUE;
                for (k = 0; k < tempColumn.length; k++){
                    if (tempColumn[k] != null && tempColumn[k] != null){
                        if (tempColumn[k] < minInColumn){
                            minInColumn = tempColumn[k];
                        }
                    }
                }
                minInColumn[i] = minInColumn;
            }
        }
    }

    var maxSum = 0;
    var idxI = 0;
    var idxJ = 0;
    for (i = 0; i < reducedMatrix.length; i++){
        for(j = 0; j < reducedMatrix.length; j++){
            if (reducedMatrix[i][j] === 0){
                var sum = minInRow[i] + minInColumn[j];
                if (sum > maxSum){
                    maxSum = sum;
                    idxI = i;
                    idxJ = j;
                }
            }
        }
    }
    var bottomCurSet = bottom + maxSum;
    reducedMatrix[idxI][idxJ] = null;
    var newReducedMatrix = getReduceMatrixForSet(reducedMatrix);
    newReducedMatrix[idxJ][idxI] = null;

    for (i = 0; i < newReducedMatrix.length; i++){
        delete newReducedMatrix[i][idxJ];
    }
    delete newReducedMatrix[idxI];

    var shortReduceMatrix = getReduceMatrixForSet(newReducedMatrix);
    if (bottom + newBottom < bottomCurSet){
        bottom += newBottom;
        var arr = [idxI, idxJ];
        resultPoints.push(arr);
        alert(resultPoints);
        console.log(resultPoints);
        //getPoint(shortReduceMatrix);
    }
}

var newBottom;
function getReduceMatrixForSet(matrixRoutes){
    newBottom = 0;
    var i = 0;
    var j = 0;
    var reducedMatrix = matrixRoutes.slice();
    /!*for (i = 0; i < matrixRoutes.length; i ++){
        reducedMatrix[i] = matrixRoutes[i].slice();
    }*!/
    var minElemsInRows = [];
    var minElemsInColumns = [];
    for(i = 0; i < reducedMatrix.length; i++){
        minElemsInRows.push(0);
        minElemsInColumns.push(0);
    }

    for (i = 0; i < reducedMatrix.length; i++){
        //var minInRow = reducedMatrix[i][i + 1];
        var minInRow = Number.MAX_VALUE;
        for(j = 0; j < reducedMatrix[i].length; j++){
            if (reducedMatrix[i][j] != null){
                if (reducedMatrix[i][j] < minInRow){
                    minInRow = reducedMatrix[i][j];
                }
            }
        }
        minElemsInRows[i] = minInRow;
        newBottom += minInRow;
    }

  /!*  for (i = 0; i < reducedMatrix.length; i++){
        for (j = 0; j < reducedMatrix[i].length; j++){
            if (reducedMatrix[i][j] != null){
                reducedMatrix[i][j] = Number((reducedMatrix[i][j] - minElemsInRows[i]).toFixed(2));
            }
        }
    }*!/

/!*    console.log("min in rows " + minElemsInRows);
    console.log("reduce row");
    console.log(reducedMatrix);*!/

    for (i = 0; i < reducedMatrix.length; i++){
        /!*var minInColumn = reducedMatrix[n + 1][n];*!/
        var minInColumn = Number.MAX_VALUE;
        for (j = 0; j < reducedMatrix.length; j++){
            if (reducedMatrix[j][i] != null){
                if (reducedMatrix[j][i] < minInColumn){
                    minInColumn = reducedMatrix[j][i];
                }
            }
        }
        minElemsInColumns[i] = minInColumn;
        newBottom += minInColumn;
    }
/!*
    for (i = 0; i < reducedMatrix.length; i++){
        for (j = 0; j < reducedMatrix[i].length; j++){
            if (reducedMatrix[j][i] != null){
                reducedMatrix[j][i] = Number((reducedMatrix[j][i] - minElemsInColumns[i]).toFixed(2));
            }
        }
    }*!/

    /!*bottom = Number(bottom.toFixed(2));
    console.log("min in columns " + minElemsInColumns);
    console.log("bottom " + bottom);
    console.log("reduce column ");
    console.log(reducedMatrix);*!/

    return reducedMatrix;
}*/


function getRouteTime(pointSequence){
    //console.log(pointSequence);
    var time = 0;
    for (var i = 0; i < pointSequence.length - 1; i++){
        time += matrixRoutes[pointSequence[i]][pointSequence[i + 1]];
        //console.log("route:" + pointSequence[i] + "->" + pointSequence[i + 1] + " time:" + time);
    }
    time += matrixRoutes[pointSequence[pointSequence.length - 1]][pointSequence[0]];
    //console.log("route:" + pointSequence[pointSequence.length - 1] + "->" + pointSequence[0] + " time:" + time);
    return time;
}

var resultPointsSequence = [];
var visited = [];
function createPointsArray(matrixRoutes, idx){
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
}



function finalRoute(finPoints) {
    ymaps.route(
        finPoints, {
            mapStateAutoApply: true,
            boundsAutoApply: true,
            avoidTrafficJams: true
        }).then(function (route) {
        route.getPaths().options.set({
            // в балуне выводим только информацию о времени движения с учетом пробок
            /*balloonContentBodyLayout: ymaps.templateLayoutFactory.createClass('$[properties.humanJamsTime]'),*/
            // можно выставить настройки графики маршруту
            balloonContentBodyLayout: 'fvfvfv',
            strokeColor: '0000ffff',
            opacity: 0.9
        });
        // добавляем маршрут на карту
        map.geoObjects.add(route);
    });
}
//-----------------------------------------------
$(document).ready(function () {

    /*var mas = [[1,2,3,4], [2,3,4,5], [3,4,5,6], [4,5,6,7]];
    mas[0].splice(2,1);
    mas[1].splice(2,1);
    mas[2].splice(2,1);
    mas[3].splice(2,1);
    mas.splice(1,1);
    console.log(mas);*/

});