$(document).ready(function () {
    $(".dropdown").hover(
        function () {
            $('.dropdown-menu', this).stop(true, true).fadeIn("fast");
            $(this).toggleClass('open');
            $('b', this).toggleClass("caret caret-up");
        },
        function () {
            $('.dropdown-menu', this).stop(true, true).fadeOut("fast");
            $(this).toggleClass('open');
            $('b', this).toggleClass("caret caret-up");
        });
    /*$(".dropdown").click(
        function() {
            if (!this.hasClass('open')){
                $('.dropdown-menu').fadeIn("fast");
                $(this).toggleClass('open');
                $('b', this).toggleClass("caret caret-up");
            }else{
                $('.dropdown-menu').fadeOut("fast");
                $(this).toggleClass('open');
                $('b', this).toggleClass("caret caret-up");
            }
        });*/
});