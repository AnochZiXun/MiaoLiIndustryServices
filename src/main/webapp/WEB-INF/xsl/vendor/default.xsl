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
			<SCRIPT src="{@contextPath}/SCRIPT/default.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/vendor.js"/>
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
							<xsl:apply-templates select="list"/>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<DIV id="header" class="cF">
				<DIV id="breadcrumb" class="fL">
					<A href="{@contextPath}/HouTai.asp">後臺首頁</A>
					<SPAN>&#187;</SPAN>
					<A>廠商</A>
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

	<xsl:template match="list">
		<FORM id="pagination" action="{@action}" method="GET">
			<TABLE class="list">
				<CAPTION class="cF">
					<LABEL>
						<SPAN>廠商名稱</SPAN>
						<INPUT name="queryString" type="text" value="{../search/@queryString}"/>
					</LABEL>
					<B class="blank">&#160;</B>
					<INPUT class="button" type="submit" value="搜尋"/>
				</CAPTION>
				<THEAD>
					<TR>
						<TH>廠商名稱</TH>
						<TH>工業區</TH>
						<TH>聯絡人電子郵件</TH>
						<TH>密碼</TH>
						<TH>修改</TH>
					</TR>
				</THEAD>
				<TFOOT>
					<TR>
						<TD colspan="5">
							<DIV class="fL">
								<A class="button" href="add.asp">新增</A>
							</DIV>
							<DIV class="fR">
								<xsl:apply-templates select="../search"/>
							</DIV>
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
								<xsl:value-of select="name"/>
							</TD>
							<TD>
								<xsl:value-of select="industrialDistrict"/>
							</TD>
							<TD class="bitstreamVeraSansMono">
								<xsl:value-of select="contactEmail"/>
							</TD>
							<TD>
								<A class="fontAwesome" href="{@id}/shadow.asp">&#xF1EC;</A>
							</TD>
							<TD>
								<A class="fontAwesome" href="{@id}.asp">&#xF044;</A>
							</TD>
						</TR>
					</xsl:for-each>
				</TBODY>
			</TABLE>
		</FORM>
	</xsl:template>

</xsl:stylesheet>