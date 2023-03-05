$(document).ready(function () {
	/**
	 * @author	P-C Lin (a.k.a 高科技黑手)
	 */
	$('FORM#searchByGoogle').submit(function (e) {
		var q = this.elements['q'], query = $('INPUT#query');
		$(q).val($(query).val() + ' site:mii.itri.org.tw');
	});

	/**
	 * @author	Rick Wu
	 */
	$('#banner img:not(:first)').css('display', 'none');
	setInterval(function () {
		$('#banner img').each(function () {
			$total = $('#banner img').size();
			if ($(this).is(':visible')) {
				$nextImg = $(this).index() + 1;
				if ($nextImg > $total - 1) {
					$nextImg = 0;
				}
			}
		});
		$('#banner img').each(function () {
			if ($(this).index() === $nextImg) {
				$(this).fadeIn(3000);
			} else {
				$(this).fadeOut(3000);
			}
		});
	}, 8000);
});