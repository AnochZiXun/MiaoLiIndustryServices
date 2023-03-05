/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	$('A.ajaxGet').click(function (e) {
		e.preventDefault();
		$.get($(this).attr('href'), function (d) {
			if (d.reason) {
				alert(d.reason);
				if (d.redirect) {
					location.href = d.redirect;
				}
			}
			if (d.response) {
				location.reload();
			}
		}, 'json');
		return false;
	});
});