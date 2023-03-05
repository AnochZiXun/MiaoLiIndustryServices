/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
$(document).ready(function () {
	if (typeof CKEDITOR !== 'undefined') {
		var editor = CKEDITOR.replace('wysiwyg', {
			toolbar: [
				{name: 'basicstyles', groups: ['basicstyles', 'cleanup'], items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat']},
				{name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi'], items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl']},
				{name: 'links', items: ['Link', 'Unlink', 'Anchor']},
				{name: 'insert', items: ['Image', 'Table', 'HorizontalRule', 'SpecialChar']},
				'/',
				{name: 'styles', items: ['Styles', 'Format', 'Font', 'FontSize']},
				{name: 'colors', items: ['TextColor', 'BGColor']},
				{name: 'tools', items: ['Maximize', 'ShowBlocks']},
				{name: 'others', items: ['-']},
				{name: 'about', items: ['Source', 'About']}
			]
		});
		CKFinder.setupCKEditor(editor, '/ckfinder/');
		CKEDITOR.on('instanceReady', function (ev) {
			ev.editor.dataProcessor.writer.selfClosingEnd = '>';
		});
	}
	if (typeof jQuery.ui !== 'undefined' && /[1-9]\.[1-9]{1,2}.[1-9]{1,2}/.test($.ui.version)) {
		$('INPUT.dP').datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat: 'yy-mm-dd'
		}).datepicker($.datepicker.regional['zh-TW']);
	}
});