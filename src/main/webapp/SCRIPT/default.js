/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	/**
	 * DELETE via AJAX
	 */
	$('A[href].ajaxDelete').click(function (e) {
		e.preventDefault();
		$.ajax({
			dataType: 'json',
			method: 'DELETE',
			success: function (d) {
				if (d.reason) {
					alert(d.reason);
				}
				if (d.redirect) {
					location.href = d.redirect;
				}
				if (d.response) {
					location.reload();
				}
			},
			url: $(this).attr('href')
		});
		return false;
	});
	/**
	 * 以 AJAX 取得 HTML 碼
	 */
	$('A[href].ajaxGetHtml').click(function (e) {
		e.preventDefault();
		$.post($(this).attr('href'), function (d) {
			$('DIV#jDialog').html(d);
		}, 'html');
		return false;
	});
	/**
	 * POST via AJAX
	 */
	$('A[href].ajaxPost').click(function (e) {
		e.preventDefault();
		$.post($(this).attr('href'), function (d) {
			if (d.reason) {
				alert(d.reason);
			}
			if (d.redirect) {
				location.href = d.redirect;
			}
			if (d.response) {
				location.reload();
			}
		}, 'json');
		return false;
	});
	/**
	 * 手風琴
	 */
	$('ASIDE#accordion').accordion({
		heightStyle: 'content'
	});
	/**
	 * 翻頁器
	 */
	$('FORM#pagination A.paginate').click(function (e) {
		e.preventDefault();
		var p = $(this).attr('tabindex');
		$(this).parents('FORM').find('SELECT[name="p"] OPTION').each(function () {
			if ($(this).val() === p) {
				$(this).siblings('OPTION:selected').removeAttr('selected');
				$(this).attr({selected: true});
				this.form.submit();
			}
		});
		return false;
	});
	/**
	 * 跳頁器
	 */
	$('FORM#pagination SELECT[name="p"]').change(function () {
		this.form.submit();
	});
	/**
	 * 排序器
	 */
	$('FORM#pagination TH>A[name]').click(function (e) {
		e.preventDefault();
		console.log(this);
		var o = $('INPUT#orderBy')[0], n = $(this).attr('name'), d = $('INPUT#direction')[0];
		if ($(o).val() === n) {
			$(d).val($(d).val().match(/^DESC$/g) === null ? 'DESC' : 'ASC');
		}
		$('INPUT#orderBy').val(n);
		$(this).parents('FORM#pagination').submit();
		return false;
	});
	/**
	 * 固定的日期顯示器
	 */
	$('DIV#jDatepicker').datepicker({
		changeMonth: true,
		changeYear: true
	});
});