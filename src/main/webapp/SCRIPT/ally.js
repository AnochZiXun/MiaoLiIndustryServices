/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	if (typeof jQuery.ui !== 'undefined' && /[1-9]\.[1-9]{1,2}.[1-9]{1,2}/.test($.ui.version)) {
		$('INPUT.dP').datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat: 'yy-mm-dd'
		}).datepicker($.datepicker.regional['zh-TW']);
	}
});