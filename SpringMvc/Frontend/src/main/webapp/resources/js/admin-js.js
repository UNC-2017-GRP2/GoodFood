var pageContext = "";
/*var costRegex = /^([1-9])([0-9])$/;*/
var costRegex = /^([1-9])\d*$/;

function changeRole(userId, buttonId) {
    var newRole = $('#' + buttonId).val();
    $.ajax({
        url : 'changeRole',
        type: 'GET',
        /*contentType: 'application/json',
        mimeType: 'application/json',*/
        data : ({
            userId: userId,
            role: newRole
        }),
        success: function () {
            $.notify(getNotificationString('role_changed'), "success");
            $("#user-role-"+userId).text(newRole);
        },
        error:function () {
            $.notify(getErrorString('role_not_changed'), "success");
        }
    });
}

function selectOption(elementId, value) {
    document.getElementById(elementId).value = value;
}

/*$(function () {
    $('.navbar-toggle-sidebar').click(function () {
        $('.navbar-nav').toggleClass('slide-in');
        $('.side-body').toggleClass('body-slide-in');
        $('#search').removeClass('in').addClass('collapse').slideUp(200);
    });

    $('#search-trigger').click(function () {
        $('.navbar-nav').removeClass('slide-in');
        $('.side-body').removeClass('body-slide-in');
        $('.search-input').focus();
    });
});

/!*ДЛЯ ПОИСКА*!/
(function(){
    'use strict';
    var $ = jQuery;
    $.fn.extend({
        filterTable: function(){
            return this.each(function(){
                $(this).on('keyup', function(e){
                    $('.filterTable_no_results').remove();
                    var $this = $(this),
                        search = $this.val().toLowerCase(),
                        target = $this.attr('data-filters'),
                        $target = $(target),
                        $rows = $target.find('tbody tr');

                    if(search === '') {
                        $rows.show();
                    } else {
                        $rows.each(function(){
                            var $this = $(this);
                            $this.text().toLowerCase().indexOf(search) === -1 ? $this.hide() : $this.show();
                        });
                        if($target.find('tbody tr:visible').size() === 0) {
                            var col_count = $target.find('tr').first().find('td').size();
                            var no_results = $('<tr class="filterTable_no_results"><td colspan="'+col_count+'">' + getErrorString('no_results') + '</td></tr>');
                            $target.find('tbody').append(no_results);
                        }
                    }
                });
            });
        }
    });
    $('[data-action="filter"]').filterTable();
})(jQuery);

$(function(){
    // attach table filter plugin to inputs
    $('[data-action="filter"]').filterTable();

    $('.container').on('click', '.panel-heading span.filter', function(e){
        var $this = $(this),
            $panel = $this.parents('.panel');

        $panel.find('.panel-body').slideToggle();
        if($this.css('display') != 'none') {
            $panel.find('.panel-body input').focus();
        }
    });
    $('[data-toggle="tooltip"]').tooltip();
});*/

function getUserInfo(userId) {
    $.ajax({
        url : 'getUserInfo',
        type: 'GET',
        data : ({
            userId : userId
        }),
        dataType:'json',
        success: function (data) {
            $("#data-role").text(data.role);
            $("#data-user-id").text(data.userId);
            $("#data-login").text(data.login);
            $("#data-fio").text(data.fio);
            $("#data-phone").text(data.phoneNumber);
            $("#data-email").text(data.email);
            if (data.birthday != null){
                $("#data-birthday").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
            }
            $.each(data.addresses, function( index, value ) {
                getAddressByCoordinates(value.latitude,value.longitude, index);
            });
        },
        error: function () {
           $.notify(getErrorString('data_error'));
        }
    });
}



function getAddressByCoordinates(latitude, longitude, index) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            var html;
            if (index === 0){
                html = $("#user-data-list").html() + "<li class=\"user-address\"><p class=\"row\"><span class=\"col-sm-1\"></span><span class=\"col-sm-3\">" + getLocStrings('addresses') + "</span><span class=\"data-address\" class=\"col-sm-6\">" + obj.getAddressLine() + "</span></p></li>";
            }else{
                html = $("#user-data-list").html() + "<li class=\"user-address\"><p class=\"row\"><span class=\"col-sm-1\"></span><span class=\"col-sm-3\"></span><span class=\"data-address\" class=\"col-sm-6\">" + obj.getAddressLine() + "</span></p></li>";
            }
            $("#user-data-list").html(html);
        }
    });
}

function removeUser(thisElem, userId) {
    $.ajax({
        url : 'removeUser',
        type: 'GET',
        data : ({
            userId: userId
        }),
        success: function () {
            $.notify(getNotificationString('user_deleted'), "success");
            //$(thisElem).parent('td').parent('tr').remove();
            var userTable = $("#users-table").DataTable();
            userTable
                .row($(thisElem).parents('tr'))
                .remove()
                .draw();
        },
        error: function () {
            $.notify(getErrorString('user_not_deleted'), "error");
        }
    });
}

function createUser() {
    var userFormArray = $("#createUserForm").serializeArray();
    var userObj = {};
    $.each(userFormArray, function(idx, el){
        userObj[el.name] = el.value;
       // userObj[el.name] ? userObj[el.name].push(el.value) : (userObj[el.name] = [el.value]);
    });
    $.ajax({
        url : 'createUser',
        type: 'GET',
        data : ({
            jsonUser : JSON.stringify(userObj)
        }),
        dataType: 'json',
        success: function (data) {
            $.notify(getNotificationString('user_created'), "success");
            $("#createUserForm").trigger('reset');
            $("#btn-signUp").prop('disabled', true);
            $('#createUserForm input').each(function(nf, form){
                $(this).css('border', '0.5px solid #b4b3b3');
            });

            var row = "<tr class=\"user-tr\" onclick=\"getUserInfo('" + data.userId + "');\">" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\">" + data.userId + "</td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\">" + data.fio + "</td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\">" + data.login + "</td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\"></td>" +
                "<td data-toggle=\"modal\" data-target=\"#user-info-modal\" id=\"user-role-"+ data.userId + "\">" + data.role + "</td>" +
                "<td class=\"text-center\">" +
                "<select id=\"dropdown-" + data.userId + "\" class=\"select-each-role\" style =\"margin-right: 5px;\">" +
                "   <option value=\"ROLE_COURIER\">" + getLocStrings('ROLE_COURIER') + "</option>" +
                "   <option value=\"ROLE_ADMIN\">" + getLocStrings('ROLE_ADMIN') + "</option>" +
                "   <option value=\"ROLE_USER\">" + getLocStrings('ROLE_USER') + "</option>" +
                "</select>" +
                "<a class='btn btn-info btn-xs' style =\"margin-right: 5px;\" href=\"#\"\n" +
                "   onclick=\"changeRole('" + data.userId + "','dropdown-" + data.userId + "');\">" +
                "       <span class=\"glyphicon glyphicon-edit\"></span>" + getLocStrings('change_role') +
                "</a>" +
                "<a href=\"#\" class=\"btn btn-danger btn-xs\"\n" +
                "   onclick=\"removeUser(this,'" + data.userId + "');\">" +
                "       <span class=\"glyphicon glyphicon-remove\"></span>" + getLocStrings('del_user') +
                "</a>" +
                "</td> </tr>";
            var userTable = $("#users-table").DataTable();
            userTable.row.add($(row)).draw();
            //$('#users-table tr:last').after(row);
        },
        error: function () {
            $.notify(getErrorString('user_not_created'), "error");
        }
    });
}

function getOrderInfo(orderId) {
    $.ajax({
        url : 'getOrderInfo',
        type: 'GET',
        data : ({
            orderId : orderId
        }),
        dataType:'json',
        success: function (data) {
            $("#order-id").text(data.orderId);
            $("#order-status").text(data.status);
            var date = new Date(data.orderCreationDate.date.year, data.orderCreationDate.date.month - 1, data.orderCreationDate.date.day, data.orderCreationDate.time.hour, data.orderCreationDate.time.minute, data.orderCreationDate.time.second);
            $("#order-date").text(date.toLocaleString());
            getOrderAddress(data.orderAddress.latitude, data.orderAddress.longitude);
            $("#order-phone").text(data.orderPhone);
            $("#order-payment-type").text(data.paymentType);
            if(data.isPaid === true){
                $("#order-paid").text(getLocStrings('order_paid'));
            }else{
                $("#order-paid").text(getLocStrings('order_not_paid'));
            }
            //alert(JSON.stringify(data));
            for (var i = 0; i < data.orderItems.length; i++){
                var newItemHtml = "<div class=\"row text-left\">" +
                    "               <div class=\"col-sm-5\">" + data.orderItems[i].productName + "</div>" +
                    "               <div class=\"col-sm-4\">" + data.orderItems[i].productQuantity + getLocStrings('items_count') + getLocStrings('multiplication_sign') + data.orderItems[i].productCost + getLocStrings('rub') +
                    "               </div>" +
                    "               <div class=\"col-sm-3\">" + data.orderItems[i].productCost*data.orderItems[i].productQuantity + getLocStrings('rub') + "</div>" +
                    "              </div>";
                $("#order-items").append(newItemHtml);
            }
            $("#order-cost").text(data.orderCost + getLocStrings('rub'));

            $.ajax({
                    url : 'getUserInfo',
                    type: 'GET',
                    data : ({
                        userId : data.userId
                    }),
                    dataType:'json',
                    success: function (data) {
                        $("#user-role-order").text(data.role);
                        $("#user-id-order").text(data.userId);
                        $("#user-login-order").text(data.login);
                        $("#user-fio-order").text(data.fio);
                        $("#user-phone-order").text(data.phoneNumber);
                        $("#user-email-order").text(data.email);
                        $("#user-birthday-order").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
                        /*$.each(data.addresses, function( index, value ) {
                            getAddressByCoordinates(value.latitude,value.longitude, index);
                        });*/
                    },
                    error: function () {
                        $.notify(getErrorString('data_error'));
                    }
            });

        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });
}

function getOrderAddress(latitude, longitude){
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            $("#order-address").text(obj.getAddressLine());
        }
    });
}

function editItem(itemId) {
    $.ajax({
        url : 'getItemInfo',
        type: 'GET',
        data : ({
            itemId : itemId
        }),
        dataType:'json',
        success: function (data) {
            $("#item-id").val(data.productId);
            $("#item-category").val(data.productCategory);
            $("#item-cost").val(data.productCost);
            $("#item-image").attr('src', pageContext + data.productImage);
        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });

    $.ajax({
        url : 'getLocItemsInfo',
        type: 'GET',
        data : ({
            itemId : itemId
        }),
        dataType:'json',
        success: function (data) {
            $("#item-name-en").val(data.nameEn);
            $("#item-description-en").val(data.descriptionEn);
            $("#item-name-ru").val(data.nameRu);
            $("#item-description-ru").val(data.descriptionRu);
            $("#item-name-uk").val(data.nameUk);
            $("#item-description-uk").val(data.descriptionUk);
        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });
}

function removeItem(thisElem, itemId) {
    $.ajax({
        url : 'delItem',
        type: 'GET',
        data : ({
            itemId: itemId
        }),
        success: function () {
            $.notify(getNotificationString('item_deleted'), "success");
            //$(thisElem).parent('td').parent('tr').remove();
            var itemTable = $("#items-table").DataTable();
            itemTable
                .row($(thisElem).parents('tr'))
                .remove()
                .draw();
        },
        error: function () {
            $.notify(getErrorString('item_not_deleted'), "error");
        }
    });
}

function removeOrder(thisElem, orderId) {
    $.ajax({
        url : 'delOrder',
        type: 'GET',
        data : ({
            orderId: orderId
        }),
        success: function () {
            $.notify(getNotificationString('order_deleted'), "success");
            var ordersTable = $("#orders-table").DataTable();
            ordersTable
                .row($(thisElem).parents('tr'))
                .remove()
                .draw();
        },
        error: function () {
            $.notify(getErrorString('order_not_deleted'), "error");
        }
    });
}

$(document).ready(function () {

    var languageTableParams = {
        "language":{
            "lengthMenu": getLocStrings('show') + " _MENU_ " + getLocStrings('entries'),
            "zeroRecords": getLocStrings('no_records found'),
            "info": getLocStrings('showing_page') + " _PAGE_ " + getLocStrings('page_of') +" _PAGES_",
            "infoEmpty": getLocStrings('no_records_available'),
            "infoFiltered": "(" + getLocStrings('filtered_from') + " _MAX_ " + getLocStrings('total_records') +")",
            "search": getLocStrings('search'),
            "processing": getLocStrings('processing'),
            "loadingRecords": getLocStrings('loading_records'),
            "emptyTable": getLocStrings('empty_table'),
            paginate: {
                first:      getLocStrings('first'),
                previous:   getLocStrings('previous'),
                next:       getLocStrings('next'),
                last:       getLocStrings('last')
            },
            aria: {
                sortAscending:  getLocStrings('sort_ascending'),
                sortDescending: getLocStrings('sort_descending')
            }
        }
    };

    var ordersTable = $("#orders-table").DataTable(languageTableParams);
    var usersTable = $("#users-table").DataTable(languageTableParams);
    var itemsTable = $("#items-table").DataTable(languageTableParams);


    $("#user-info-modal").on("hide.bs.modal", function () {
        $(".user-address").remove();
        $(".user-value").text("");
    });

    $("#order-info-modal").on("hide.bs.modal", function () {
        $("#order-items div").remove();
        $(".user-value-order").text("");
        $(".order-value").text("");
    });

    $(".item-param").keyup(function () {
        if ($(this).val() === ""){
            $(this).css("border", "0.5px solid #d9534f");
        }else{
            $(this).css("border", "0.5px solid #5cb85c");
        }
    });

    $('#item-cost').keyup(function () {
        if ($(this).val() === "0"){
            $(this).val("");
            $(this).css("border", "0.5px solid #d9534f");
        }
        var testText = $(this).val();
        if (testText*1 + 0 != $(this).val()){
            $(this).val(testText.substring(0, testText.length - 1));
        }
    });

  /*  $("#my-file-selector").onchange = function () {
        var fileName = this.value;
        var fileExtension = fileName.substr(fileName.length - 4);

        console.log(fileExtension);
        if (fileExtension !== ".xls") {
            alert("That ain't no .xls file!");
        }else{
            $('#upload-file-info').html(fileName)
        }
    };*/

    $("#my-file-selector").change(function () {
        var fileName = this.files[0].name;
        var fileType = this.files[0].type;
        if (fileType !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && fileType !== "application/vnd.ms-excel") {
            $.notify(getErrorString('invalid_file'), "error");
            this.value = "";
            $('#upload-file-info').html("");
        } else {
            $('#upload-file-info').html(fileName);
        }
    });

    $( "#add-item-form" ).submit(function( event ) {
        if ( $("#my-file-selector").val() === "" ) {
            $.notify(getErrorString('invalid_file'), "error");
            event.preventDefault();
        }else{
            $( "#add-item-form" ).submit();
        }
    });


    /*CHARTS*/
});



