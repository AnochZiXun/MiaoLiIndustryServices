/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	$('FORM').submit(function (e) {
		e.preventDefault();
		if (window.confirm('確定已處理完畢，要刪除此項目嗎？')) {
			$.post($(this).attr('action'), function (d) {
				if (d.reason) {
					alert(d.reason);
					if (d.redirect) {
						location.href = d.redirect;
					}
				}
				if (d.response) {
					location.href = d.redirect ? d.redirect : './';
				}
			}, 'json');
		}
		return false;
	});
});