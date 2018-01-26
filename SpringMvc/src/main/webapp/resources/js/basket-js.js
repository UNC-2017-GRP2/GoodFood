

$(document).ready(function() {

    $('.minus').click(function () {
        var $input = $(this).parent().find('input');
        var count = parseInt($input.val()) - 1;
        var previousCount = parseInt($input.val());
        count = count < 1 ? 1 : count;
        $input.val(count);
        $input.change();
        var itemId = $(this).attr("item-id");

        var itemCost = $(this).attr("item-cost");
        var newValue = count*itemCost;
        var $final = $(this).parent().parent().parent().parent().parent().find('.final-item-cost-span');
        $final.text(newValue);

        var $totalCostElem = $(".total-order-cost");
        var previousItemsCost = previousCount*itemCost;
        var totalCost = parseInt($totalCostElem.text());
        totalCost = totalCost-previousItemsCost+newValue;
        $totalCostElem.text(totalCost);

        $.ajax({
            url : 'updateBasket',
            type: 'GET',
            contentType: 'application/json',
            mimeType: 'application/json',
            data : ({
                itemId: itemId,
                newQuantity: count
            }),
            success: function (data) {
                alert(data);
            }
        });
        return false;
    });

    $('.plus').click(function () {
        var $input = $(this).parent().find('input');
        var count = parseInt($input.val()) + 1;
        var previousCount = parseInt($input.val());
        $input.val(count);
        $input.change();
        var itemId = $(this).attr("item-id");

        var itemCost = $(this).attr("item-cost");
        var newValue = count*itemCost;
        var $final = $(this).parent().parent().parent().parent().parent().find('.final-item-cost-span');
        $final.text(newValue);

        var $totalCostElem = $(".total-order-cost");
        var previousItemsCost = previousCount*itemCost;
        var totalCost = parseInt($totalCostElem.text());
        totalCost = totalCost-previousItemsCost+newValue;
        $totalCostElem.text(totalCost);

        $.ajax({
            url : 'updateBasket',
            type: 'GET',
            contentType: 'application/json',
            mimeType: 'application/json',
            data : ({
                itemId: itemId,
                newQuantity: count
            }),
            success: function (data) {
                alert(data);
            }
        });


        /*$.getJSON("updateBasket", { itemId: itemId})
            .done(function (data) {
                alert(data);
            })
            .fail(function () {
                alert("Error GetUsersRoute");
            });*/
        return false;
    });

    $('.remove-item').click(function () {
        $(this).parent().parent().remove();
        var itemId = $(this).attr("item-id");
        $.ajax({
            url : 'removeItem',
            type: 'GET',
            contentType: 'application/json',
            mimeType: 'application/json',
            data : ({
                itemId: itemId
            }),
            success: function (data) {
                $(".total-order-cost").text(data);
            }
        });

        $.ajax({
            url : 'isBasketEmpty',
            type: 'GET',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                /*alert(data);
                if (data == "true"){
                    alert("true");
                    $('.to-order').prop("disabled", false);
                }else{
                    alert("false");
                }*/
            }
        });
    });
});