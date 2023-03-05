/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	$('INPUT.numeric').keyup(function () {
		$(this).val($(this).val().replace(/\D/g, ''));
	});
});