<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<xsl:import href="/common.xsl"/>

	<xsl:template match="/">
		<xsl:text disable-output-escaping="yes">&#60;!DOCTYPE HTML&#62;</xsl:text>
		<HTML dir="ltr" lang="zh-TW">
			<xsl:apply-templates/>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<HEAD>
			<META charset="UTF-8"/>
			<META content="width=device-width,initial-scale=1.0" name="viewport"/>
			<LINK href="//ajax.googleapis.com/ajax/libs/jqueryui/{@jQueryUI}/themes/dark-hive/jquery-ui.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/default.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/aDMiNiSTRaTioN.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/{@jQuery}/jquery.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/{@jQueryUI}/jquery-ui.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/{@jQueryUI}/i18n/jquery-ui-i18n.min.js"/>
			<SCRIPT src="//cdn.ckeditor.com/4.4.7/full/ckeditor.js"/>
			<SCRIPT src="{@contextPath}/ckfinder/ckfinder.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/default.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/ally.js"/>
			<TITLE>
				<xsl:value-of select="@title" disable-output-escaping="yes"/>
			</TITLE>
		</HEAD>
		<xsl:comment>
			<xsl:value-of select="system-property('xsl:version')"/>
		</xsl:comment>
		<BODY>
			<TABLE id="contentWrapper">
				<TBODY>
					<TR>
						<TD>
							<xsl:apply-templates select="aside">
								<xsl:with-param name="contextPath" select="@contextPath"/>
							</xsl:apply-templates>
						</TD>
						<TD>
							<xsl:apply-templates select="form"/>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<DIV id="header" class="cF">
				<DIV id="breadcrumb" class="fL">
					<A href="{@contextPath}/HouTai.asp">後臺首頁</A>
					<SPAN>&#187;</SPAN>
					<A>友站連結</A>
				</DIV>
				<DIV class="fR">
					<A>
						<xsl:value-of select="@myName"/>
					</A>
					<SPAN>&#187;</SPAN>
					<A href="{@contextPath}/DengChu.asp">登出</A>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

	<xsl:template match="form">
		<FORM action="{@action}" method="POST" enctype="multipart/form-data">
			<FIELDSET>
				<LEGEND>
					<xsl:value-of select="@legend"/>
				</LEGEND>
				<TABLE class="form">
					<CAPTION>
						<xsl:value-of select="@errorMessage"/>
					</CAPTION>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(title)}">標題</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(title)}" name="title" type="text" value="{title}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(phone)}">聯絡電話</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(phone)}" name="phone" type="text" value="{phone}"/>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(image)}">縮圖</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(image)}" name="image" type="file" value="{image}"/>
						</TD>
						<TD>必選；176&#215;52或等比例的點陣圖。</TD>
					</TR>
					<xsl:if test="id">
						<TR>
							<TD colspan="3">
								<IMG alt="" height="52" src="{id}.png" width="176"/>
							</TD>
						</TR>
					</xsl:if>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(fqdn)}">參考網址</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(fqdn)}" name="fqdn" type="text" value="{fqdn}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(since)}">上架日期</LABEL>
						</TH>
						<TD>
							<INPUT class="dP" id="{generate-id(since)}" name="since" readonly="" type="text" value="{since}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(until)}">下架日期</LABEL>
						</TH>
						<TD>
							<INPUT class="dP" id="{generate-id(until)}" name="until" readonly="" type="text" value="{until}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">發佈審查</TH>
						<TD>
							<LABEL>
								<INPUT name="censored" type="radio" value="false">
									<xsl:if test="censored='false'">
										<xsl:attribute name="checked"/>
									</xsl:if>
								</INPUT>
								<SPAN>審核中</SPAN>
							</LABEL>
							<LABEL>
								<INPUT name="censored" type="radio" value="true">
									<xsl:if test="censored='true'">
										<xsl:attribute name="checked"/>
									</xsl:if>
								</INPUT>
								<SPAN>已結案</SPAN>
							</LABEL>
						</TD>
						<TD>必選。</TD>
					</TR>
					<TR>
						<TD colspan="3">
							<INPUT class="fL" type="reset" value="復原"/>
							<INPUT class="fR" type="submit" value="確定"/>
						</TD>
					</TR>
				</TABLE>
			</FIELDSET>
		</FORM>
	</xsl:template>

</xsl:stylesheet>