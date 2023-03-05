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
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/{@jQuery}/jquery.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/{@jQueryUI}/jquery-ui.min.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/default.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/counseling.js"/>
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
							<LABEL for="{generate-id(taiwaneseYear)}">年度</LABEL>
						</TH>
						<TD>
							<INPUT class="numeric" id="{generate-id(taiwaneseYear)}" maxlength="3" name="taiwaneseYear" type="text" value="{taiwaneseYear}"/>
						</TD>
						<TD>必填且須為中華民國年度。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(name)}">計畫名稱</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(name)}" name="name" type="text" value="{name}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(vendor)}">受輔導廠商</LABEL>
						</TH>
						<TD>
							<SELECT id="{generate-id(vendor)}" name="vendor">
								<OPTION value="">(無)</OPTION>
								<xsl:apply-templates select="vendor/*"/>
							</SELECT>
						</TD>
						<TD>必選。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(amount)}">補助金額</LABEL>
						</TH>
						<TD>
							<INPUT class="numeric" id="{generate-id(amount)}" name="amount" type="text" value="{amount}"/>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(condition)}">業者現況</LABEL>
						</TH>
						<TD>
							<TEXTAREA id="{generate-id(condition)}" cols="1" name="condition" rows="1">
								<xsl:value-of select="condition"/>
							</TEXTAREA>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(items)}">輔導項目</LABEL>
						</TH>
						<TD>
							<TEXTAREA id="{generate-id(items)}" cols="1" name="items" rows="1">
								<xsl:value-of select="items"/>
							</TEXTAREA>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(performance)}">輔導效益</LABEL>
						</TH>
						<TD>
							<TEXTAREA id="{generate-id(performance)}" cols="1" name="performance" rows="1">
								<xsl:value-of select="performance"/>
							</TEXTAREA>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(photo)}">相關照片</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(photo)}" name="photo" type="file"/>
							<xsl:if test="string-length(photo)&gt;'0'">
								<BR/>
								<IMG alt="相關照片" width="320" src="{photo}.png"/>
							</xsl:if>
						</TD>
						<TD>非必選但尺寸會被系統調整。</TD>
					</TR>
					<TR>
						<TH class="must">案件狀態</TH>
						<TD>
							<LABEL>
								<INPUT name="closed" type="radio" value="false">
									<xsl:if test="closed='false'">
										<xsl:attribute name="checked"/>
									</xsl:if>
								</INPUT>
								<SPAN>審核中</SPAN>
							</LABEL>
							<LABEL>
								<INPUT name="closed" type="radio" value="true">
									<xsl:if test="closed='true'">
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