function openDetails(name, image, description, cost, rub) {
    $('#itemName').text(name);
    $('#itemImage').attr("src", image);
    $('#itemDescription').text(description);
    $('#itemCost').text(cost + " " + rub);
    $('#itemDetails').modal('show');
}

function addToCart(productId, countId) {
    $.ajax({
        url : 'addBasket',
        type: 'GET',
        data : ({
            id: productId,
            count: $('#' + countId).val()
        }),
        success: function (data) {
            $.notify(getNotificationString('item_added'), "success");
        }
    });
}

function callDriver() {
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

});