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

$(function () {
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

/*ДЛЯ ПОИСКА*/
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
});

function getUserInfo(userId) {
    $.ajax({
        url : 'getUserInfo',
        type: 'GET',
        data : ({
            userId : userId
        }),
        dataType:'json',
        success: function (data) {
            //alert(data.userId);
            //var user = JSON.parse(data);
            $("#data-role").text(data.role);
            //alert(user.userId.toString());
            $("#data-user-id").text(data.userId);
            $("#data-login").text(data.login);
            $("#data-fio").text(data.fio);
            $("#data-phone").text(data.phoneNumber);
            $("#data-email").text(data.email);
            $("#data-birthday").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
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
            $(thisElem).parent('td').parent('tr').remove();
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
            //var user = JSON.parse(data);
            /*var tr = document.createElement("tr");
            tr.className = "user-tr";
            tr.onclick = function () {
                getUserInfo("'" + user.userId + "'");
            };

            /!*var cells = [];
            for (var i = 0; i < 6; i++){
                var cell = document.createElement("td");
                cells.push(cell);
            }*!/

            var cell0 = document.createElement("td");
            var cell1 = document.createElement("td");
            var cell2 = document.createElement("td");
            var cell3 = document.createElement("td");
            var cell4 = document.createElement("td");
            var cell5 = document.createElement("td");

            var dataToggle = document.createAttribute("data-toggle");
            dataToggle.value = "model";
            var dataTarget = document.createAttribute("data-target");
            dataTarget.value = "#user-info-modal";

            /!*cell0.setAttributeNode(dataToggle);
            cell0.setAttributeNode(dataTarget);

            cell1.setAttributeNode(dataToggle);
            cell1.setAttributeNode(dataTarget);

            cell2.setAttributeNode(dataToggle);
            cell2.setAttributeNode(dataTarget);

            cell3.setAttributeNode(dataToggle);
            cell3.setAttributeNode(dataTarget);

            cell4.setAttributeNode(dataToggle);
            cell4.setAttributeNode(dataTarget);*!/

            cell0.setAttribute("data-toggle", "model");
            cell0.setAttribute("data-target", "#user-info-modal");

            cell1.setAttribute("data-toggle", "model");
            cell1.setAttribute("data-target", "#user-info-modal");

            cell2.setAttribute("data-toggle", "model");
            cell2.setAttribute("data-target", "#user-info-modal");

            cell3.setAttribute("data-toggle", "model");
            cell3.setAttribute("data-target", "#user-info-modal");

            cell4.setAttribute("data-toggle", "model");
            cell4.setAttribute("data-target", "#user-info-modal");



            /!*for (var j = 0; j < cells.length - 1; j++){
                /!*cells[i].setAttribute(dataToggle);
                cells[i].setAttribute(dataTarget);*!/
                cells[i].setAttribute('data-toggle', 'model');
                cells[i].setAttribute('data-target', '#user-info-modal');
            }*!/



            /!*cells[0].appendChild(document.createTextNode(user.userId));
            cells[1].appendChild(document.createTextNode(user.fio));
            cells[2].appendChild(document.createTextNode(user.login));
            cells[3].appendChild(document.createTextNode(user.phoneNumber));
            cells[4].appendChild(document.createTextNode(user.role));
            cells[5].className = "text-center";*!/

            cell0.appendChild(document.createTextNode(user.userId));
            cell1.appendChild(document.createTextNode(user.fio));
            cell2.appendChild(document.createTextNode(user.login));
            cell3.appendChild(document.createTextNode(""));
            cell4.appendChild(document.createTextNode(user.role));
            cell5.className = "text-center";

            var selectRole = document.createElement("select");
            selectRole.id = "dropdown-" + user.userId;
            selectRole.className = "select-each-role";
            var optionsRoleText = ["Courier", "Administrator", "User"];
            var optionsRoleValue = ["ROLE_COURIER", "ROLE_ADMIN", "ROLE_USER"];
            for (var k = 0; k < optionsRoleText.length; k++){
                var option = document.createElement("option");
                option.value = optionsRoleValue[k];
                option.text = optionsRoleText[k];
                selectRole.appendChild(option);
            }

            var aChangeRole = document.createElement("a");
            aChangeRole.className = "btn btn-info btn-xs";
            aChangeRole.href = "#";
            aChangeRole.onclick = function () {
                changeRole("'" + user.userId + "'", "'dropdown-" + user.userId + "'");
            };
            var aChangeRoleSpan = document.createElement("span");
            aChangeRoleSpan.className = "glyphicon glyphicon-edit";
            aChangeRole.appendChild(aChangeRoleSpan);
            aChangeRole.appendChild(document.createTextNode("Change role"));

            var aRemoveUser = document.createElement("a");
            aRemoveUser.className = "btn btn-danger btn-xs";
            aRemoveUser.href = "#";
            aRemoveUser.onclick = function () {
                removeUser(this, "'" + user.userId + "'");
            };
            var aRemoveUserSpan = document.createElement("span");
            aRemoveUserSpan.className = "glyphicon glyphicon-remove";
            aRemoveUser.appendChild(aRemoveUserSpan);
            aRemoveUser.appendChild(document.createTextNode("Del"));

            /!*cells[5].appendChild(selectRole);
            cells[5].appendChild(aChangeRoleSpan);
            cells[5].appendChild(aRemoveUser);*!/

            cell5.appendChild(selectRole);
            cell5.appendChild(aChangeRole);
            cell5.appendChild(aRemoveUser);

            /!*for (var m = 0; m < cells.length; m++){
                tr.appendChild(cells[m]);
            }*!/

            tr.appendChild(cell0);
            tr.appendChild(cell1);
            tr.appendChild(cell2);
            tr.appendChild(cell3);
            tr.appendChild(cell4);
            tr.appendChild(cell5);

            //$("#dev-table").appendChild(tr);
            $('#dev-table tr:last').after(tr);*/

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
            $('#dev-table tr:last').after(row);
        },
        error: function () {
            $.notify(getErrorString('user_not_created'), "error");
        }
    });
}

function getOrderInfo(orderId) {
    alert(orderId);
    /*$.ajax({
        url : 'getOrderInfo',
        type: 'GET',
        data : ({
            orderId : orderId
        }),
        dataType:'json',
        success: function (data) {
            //alert(data.userId);
            //var user = JSON.parse(data);
            /!*$("#data-role").text(data.role);
            //alert(user.userId.toString());
            $("#data-user-id").text(data.userId);
            $("#data-login").text(data.login);
            $("#data-fio").text(data.fio);
            $("#data-phone").text(data.phoneNumber);
            $("#data-email").text(data.email);
            $("#data-birthday").text(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day).toLocaleDateString());
            $.each(data.addresses, function( index, value ) {
                getAddressByCoordinates(value.latitude,value.longitude, index);
            });*!/
        },
        error: function () {
            $.notify(getErrorString('data_error'));
        }
    });*/
}

$(document).ready(function () {
    $("#user-info-modal").on("hide.bs.modal", function () {
        $(".user-address").remove();
        $(".user-value").text("");
    });
    /*CHARTS*/
});



