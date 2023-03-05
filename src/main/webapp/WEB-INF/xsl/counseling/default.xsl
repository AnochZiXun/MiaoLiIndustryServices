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
			<LINK href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/dark-hive/jquery-ui.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/default.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/aDMiNiSTRaTioN.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/counseling.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/default.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/counseling.js"/>
			<TITLE>後臺 &#187; 輔導案例</TITLE>
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
					<A>輔導案例</A>
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
						<SPAN>年度</SPAN>
						<INPUT class="bitstreamVeraSansMono numeric" maxlength="3" name="taiwaneseYear" type="text" value="{../search/@taiwaneseYear}"/>
					</LABEL>
					<B class="blank">&#160;</B>
					<LABEL>
						<SPAN>計畫名稱</SPAN>
						<INPUT name="queryString" type="text" value="{../search/@queryString}"/>
					</LABEL>
					<B class="blank">&#160;</B>
					<LABEL>
						<INPUT name="closed" type="radio" value="false">
							<xsl:if test="../search/@closed='false'">
								<xsl:attribute name="checked"/>
							</xsl:if>
						</INPUT>
						<SPAN>審核中</SPAN>
					</LABEL>
					<LABEL>
						<INPUT name="closed" type="radio" value="true">
							<xsl:if test="../search/@closed='true'">
								<xsl:attribute name="checked"/>
							</xsl:if>
						</INPUT>
						<SPAN>已結案</SPAN>
					</LABEL>
					<B class="blank">&#160;</B>
					<INPUT class="button" type="submit" value="搜尋"/>
				</CAPTION>
				<THEAD>
					<TR>
						<TH>年度</TH>
						<TH>計畫名稱</TH>
						<TH>受輔導廠商</TH>
						<TH>廠商地址</TH>
						<TH>廠商電話</TH>
						<TH>補助金額</TH>
						<TH>案件狀態</TH>
						<TH>修改</TH>
					</TR>
				</THEAD>
				<TFOOT>
					<TR>
						<TD colspan="8">
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
							<TD class="bitstreamVeraSansMono">
								<xsl:value-of select="taiwaneseYear"/>
							</TD>
							<TD>
								<xsl:value-of select="name"/>
							</TD>
							<TD>
								<xsl:value-of select="vendorName"/>
							</TD>
							<TD>
								<xsl:value-of select="vendorAddress"/>
							</TD>
							<TD class="bitstreamVeraSansMono">
								<xsl:value-of select="vendorNumber"/>
							</TD>
							<TD class="bitstreamVeraSansMono">
								<xsl:if test="string-length(amount)&gt;'0'">NT&#36;<xsl:value-of select="format-number(amount,'###,###')"/></xsl:if>
							</TD>
							<TD>
								<xsl:choose>
									<xsl:when test="closed='false'">審核中</xsl:when>
									<xsl:otherwise>已結案</xsl:otherwise>
								</xsl:choose>
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