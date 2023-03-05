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
			<STYLE><![CDATA[TEXTAREA{width:320px;height:240px}]]></STYLE>
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
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<DIV id="header" class="cF">
				<DIV id="breadcrumb" class="fL">
					<A href="{@contextPath}/HouTai.asp">後臺首頁</A>
					<SPAN>&#187;</SPAN>
					<A>單位</A>
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
							<LABEL for="{generate-id(clan)}">群組</LABEL>
						</TH>
						<TD>
							<SELECT id="{generate-id(clan)}" name="clan">
								<OPTION value="">(無)</OPTION>
								<xsl:apply-templates select="clan/*"/>
							</SELECT>
						</TD>
						<TD>必選。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(name)}">人員姓名</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(name)}" name="name" type="text" value="{name}"/>
						</TD>
						<TD>必填。</TD>
					</TR>
					<TR>
						<TH class="must">
							<LABEL for="{generate-id(email)}">電子郵件(帳號)</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(email)}" name="email" type="text" value="{email}"/>
						</TD>
						<TD>必填且不得有重複電子郵件(帳號)。</TD>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(contactNumber)}">聯絡電話</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactNumber)}" name="contactNumber" type="text" value="{contactNumber}"/>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(contactCellular)}">聯絡手機</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(contactCellular)}" name="contactCellular" type="text" value="{contactCellular}"/>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(major)}">系/所/中心</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(major)}" name="major" type="text" value="{major}"/>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(title)}">職稱</LABEL>
						</TH>
						<TD>
							<INPUT id="{generate-id(title)}" name="title" type="text" value="{title}"/>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(institution)}">單位</LABEL>
						</TH>
						<TD>
							<SELECT id="{generate-id(institution)}" name="institution">
								<OPTION value="">(無)</OPTION>
								<xsl:apply-templates select="institution/*"/>
							</SELECT>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(specialty)}">專長</LABEL>
						</TH>
						<TD>
							<TEXTAREA id="{generate-id(specialty)}" cols="1" name="specialty" rows="1">
								<xsl:value-of select="specialty"/>
							</TEXTAREA>
						</TD>
						<TD/>
					</TR>
					<TR>
						<TH>
							<LABEL for="{generate-id(experience)}">經歷</LABEL>
						</TH>
						<TD>
							<TEXTAREA id="{generate-id(experience)}" cols="1" name="experience" rows="1">
								<xsl:value-of select="experience"/>
							</TEXTAREA>
						</TD>
						<TD/>
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