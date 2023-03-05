/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	$('SELECT[name="p"]').change(function () {
		this.form.submit();
	});
});