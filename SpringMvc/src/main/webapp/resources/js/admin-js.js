function dropdownButton(userId, buttonId) {
    $.ajax({
        url : 'changeRole',
        type: 'GET',
        /*contentType: 'application/json',
        mimeType: 'application/json',*/
        data : ({
            userId: userId,
            role: $('#' + buttonId).val()
        }),
        success: function (data) {
            $.notify(getNotificationString('role_changed'), "success");
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

                    if(search == '') {
                        $rows.show();
                    } else {
                        $rows.each(function(){
                            var $this = $(this);
                            $this.text().toLowerCase().indexOf(search) === -1 ? $this.hide() : $this.show();
                        })
                        if($target.find('tbody tr:visible').size() === 0) {
                            var col_count = $target.find('tr').first().find('td').size();
                            var no_results = $('<tr class="filterTable_no_results"><td colspan="'+col_count+'">No results found</td></tr>')
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
        success: function (data) {
            var user = JSON.parse(data);
            $("#data-user-id").text(user.userId);
            $("#data-login").text(user.login);
            $("#data-fio").text(user.fio);
            $("#data-phone").text(user.phoneNumber);
            $("#data-email").text(user.email);
            $("#data-birthday").text(new Date(user.birthday.year, user.birthday.month - 1, user.birthday.day).toLocaleDateString());
            $("#data-role").text(user.role);
            $.each(user.addresses, function( index, value ) {
                getAddressByCoordinates(value.latitude,value.longitude, index);
            });
        },
        error: function (data) {
            alert("error");
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
                html = $("#user-data-list").html() + "<li class=\"user-address\"><p class=\"row\"><span class=\"col-sm-1\"></span><span class=\"col-sm-3\">Addresses</span><span class=\"data-address\" class=\"col-sm-6\">" + obj.getAddressLine() + "</span></p></li>";
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
            $.notify("User deleted", "success");
            $(thisElem).parent('td').parent('tr').remove();
        },
        error: function () {
            $.notify("User was not deleted", "error");
        }
    });
}

$(document).ready(function () {
    $("#user-info-modal").on("hide.bs.modal", function () {
        $(".user-address").remove();
        $(".user-value").text("");
    });

    /*$('.selectpicker').selectpicker({
        size : 5
    });*/

});



