<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<xsl:include href="/common.xsl"/>

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
			<SCRIPT src="{@contextPath}/SCRIPT/default.js"/>
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
							<xsl:if test="list">
								<HR/>
								<xsl:apply-templates select="list">
									<xsl:with-param name="contextPath" select="@contextPath"/>
								</xsl:apply-templates>
							</xsl:if>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<DIV id="header" class="cF">
				<DIV id="breadcrumb" class="fL">
					<A href="{@contextPath}/HouTai.asp">後臺首頁</A>
					<SPAN>&#187;</SPAN>
					<A>手風琴</A>
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
		<xsl:param name="contextPath" select="contextPath"/>
		<FORM action="{@action}" method="POST">
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
							<LABEL for="{generate-id(name)}">手風琴名稱</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(name)}" name="name" type="text" value="{name}"/>
						</TD>
						<TD>必填且不得有重複名稱。</TD>
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

	<xsl:template match="list">
		<xsl:param name="contextPath" select="contextPath"/>
		<TABLE class="list">
			<CAPTION>路由</CAPTION>
			<THEAD>
				<TR>
					<TH>顯示名稱</TH>
					<TH>連結</TH>
					<TH>修改</TH>
				</TR>
			</THEAD>
			<TFOOT>
				<TR>
					<TD colspan="3">
						<DIV class="fL">
							<A class="button" href="{@id}/add.asp">新增</A>
						</DIV>
						<BR class="cF"/>
					</TD>
				</TR>
			</TFOOT>
			<TBODY>
				<xsl:for-each select="row">
					<TR>
						<xsl:if test="position()mod'2'='0'">
							<xsl:attribute name="class">odd</xsl:attribute>
						</xsl:if>
						<TD>
							<xsl:value-of select="label"/>
						</TD>
						<TD class="monospace textAlignLeft">
							<xsl:value-of select="hyperlink"/>
						</TD>
						<TD>
							<A class="fontAwesome" href="{$contextPath}/LuYou/{@id}.asp">&#xF044;</A>
						</TD>
					</TR>
				</xsl:for-each>
			</TBODY>
		</TABLE>
	</xsl:template>

</xsl:stylesheet>