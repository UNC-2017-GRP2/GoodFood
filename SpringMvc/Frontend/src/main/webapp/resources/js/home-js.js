var countRegex = /^\d*$/;

function openDetails(name, image, description, cost, rub) {
    $('#itemName').text(name);
    $('#itemImage').attr("src", image);
    $('#itemDescription').text(description);
    $('#itemCost').text(cost + " " + rub);
    $('#itemDetails').modal('show');
}

function addToCart(productId, countId) {
    $.ajax({
        url: 'addBasket',
        type: 'GET',
        data: ({
            id: productId,
            count: $('#' + countId).val()
        }),
        success: function (data) {
            $.notify(getNotificationString('item_added'), "success");
        }
    });
}

function callDriver() {
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });


    $.ajax({
        url : 'drunk',
        type: 'GET',
        success: function (data) {
            $.notify(getNotificationString('item_added'), "success");
        }
    });
}

function successNotification() {
    $(".alert").alert();
    setTimeout(function () {
        $(".alert").alert('close');
    }, 3000);
}

$(document).ready(function () {

    $('.minus').click(function () {
        var $input = $(this).parent().find('input');
        var count = parseInt($input.val()) - 1;
        count = count < 1 ? 1 : count;
        $input.val(count);
        $input.change();
        return false;
    });
    $('.plus').click(function () {
        var $input = $(this).parent().find('input');
        $input.val(parseInt($input.val()) + 1);
        $input.change();
        return false;
    });

    $('.quantity').keyup(function () {
        if ($(this).val() === "0"){
            $(this).val("1");
        }
        if ($(this).val() === " "){
            $(this).val("");
        }
        var testText = $(this).val();
        if (testText*1 + 0 != $(this).val()){
            $(this).val(testText.substring(0, testText.length - 1));
        }
    });

    var previousItemCount;
    $('.quantity').focus(function () {
        previousItemCount = $(this).val();
        $(this).val("");
    }).blur(function () {
        var count = $(this).val();
        if (count === ""){
            $(this).val(previousItemCount)
        }
    });
});