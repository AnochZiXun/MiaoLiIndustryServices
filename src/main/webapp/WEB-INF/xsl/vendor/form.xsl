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
							<LABEL for="{generate-id(name)}">廠商名稱</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(name)}" name="name" type="text" value="{name}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(unifiedBusinessNumber)}">統一編號</LABEL>
						</TH>
						<TD>
							<INPUT class="numeric" id="{generate-id(unifiedBusinessNumber)}" maxlength="8" name="unifiedBusinessNumber" type="text" value="{unifiedBusinessNumber}"/>
						</TD>
						<TD>必填且為登入用帳號。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(address)}">廠商地址</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(address)}" name="address" type="text" value="{address}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(industrialDistrict)}">工業區</LABEL>
						</TH>
						<TD>
							<SELECT id="{generate-id(industrialDistrict)}" name="industrialDistrict">
								<OPTION value="">(無)</OPTION>
								<xsl:apply-templates select="industrialDistrict/*"/>
							</SELECT>
						</TD>
						<TD>非必選。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(employeeCount)}">員工人數</LABEL>
						</TH>
						<TD>
							<INPUT class="numeric" id="{generate-id(employeeCount)}" maxlength="4" name="employeeCount" type="text" value="{employeeCount}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(contactTitle)}">聯絡人職稱</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactTitle)}" name="contactTitle" type="text" value="{contactTitle}"/>
						</TD>
						<TD>非必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(contactName)}">聯絡人姓名</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactName)}" name="contactName" type="text" value="{contactName}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(contactNumber)}">聯絡人電話</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactNumber)}" name="contactNumber" type="text" value="{contactNumber}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(contactEmail)}">聯絡人電子郵件</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactEmail)}" name="contactEmail" type="text" value="{contactEmail}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(contactFax)}">聯絡人傳真</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactFax)}" name="contactFax" type="text" value="{contactFax}"/>
						</TD>
						<TD>非必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(capital)}">資本額</LABEL>
						</TH>
						<TD>
							<INPUT class="numeric" id="{generate-id(capital)}" maxlength="9" name="capital" type="text" value="{capital}"/>
						</TD>
						<TD>非必填。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(turnover)}">營業額</LABEL>
						</TH>
						<TD>
							<INPUT class="numeric" id="{generate-id(turnover)}" maxlength="9" name="turnover" type="text" value="{turnover}"/>
						</TD>
						<TD>非必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(business)}">營業項目</LABEL>
						</TH>
						<TD>
							<TEXTAREA id="{generate-id(business)}" cols="1" name="business" rows="1">
								<xsl:value-of select="business"/>
							</TEXTAREA>
						</TD>
						<TD>必填。</TD>
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