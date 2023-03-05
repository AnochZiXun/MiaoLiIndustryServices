<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
		doctype-system="http://www.w3.org/TR/html4/loose.dtd"
		indent="no"
		media-type="text/html"
	/>

	<xsl:template match="/">
		<HTML dir="ltr" lang="zh-TW">
			<xsl:apply-templates/>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<HEAD>
			<META http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
			<META http-equiv="Content-Language" content="zh-TW"/>
			<META http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
			<META content="width=device-width,initial-scale=1.0" name="viewport"/>
			<LINK href="//ajax.googleapis.com/ajax/libs/jqueryui/{@jQueryUI}/themes/dark-hive/jquery-ui.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/default.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/aDMiNiSTRaTioN.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/{@jQuery}/jquery.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/{@jQueryUI}/jquery-ui.min.js"/>
			<SCRIPT src="{@contextPath}/SCRIPT/default.js"/>
			<TITLE>和電腦商人交易</TITLE>
		</HEAD>
		<xsl:comment>
			<xsl:value-of select="system-property('xsl:version')"/>
		</xsl:comment>
		<BODY>
			<xsl:apply-templates select="form">
				<xsl:with-param name="contextPath" select="@contextPath"/>
			</xsl:apply-templates>
		</BODY>
	</xsl:template>

	<xsl:template match="form">
		<FORM action="{@action}" method="POST">
			<TABLE border="1" cellspacing="3" cellpadding="3">
				<THEAD>
					<TR>
						<TH>木材</TH>
						<TH>泥土</TH>
						<TH>鐵塊</TH>
						<TH>糧食</TH>
						<TH>總共</TH>
					</TR>
				</THEAD>
				<TFOOT>
					<TR>
						<TD>
							<INPUT readonly="" type="text" value="{lumber}"/>
						</TD>
						<TD>
							<INPUT readonly="" type="text" value="{clay}"/>
						</TD>
						<TD>
							<INPUT readonly="" type="text" value="{iron}"/>
						</TD>
						<TD>
							<INPUT readonly="" type="text" value="{crop}"/>
						</TD>
						<TD>
							<INPUT readonly="" type="text" value="{total}"/>
						</TD>
					</TR>
				</TFOOT>
				<TBODY>
					<TR>
						<TD>
							<INPUT type="text" name="lumber" value="{@lumber}"/>
						</TD>
						<TD>
							<INPUT type="text" name="clay" value="{@clay}"/>
						</TD>
						<TD>
							<INPUT type="text" name="iron" value="{@iron}"/>
						</TD>
						<TD>
							<INPUT type="text" name="crop" value="{@crop}"/>
						</TD>
						<TD>
							<INPUT type="text" name="total" value="{@total}"/>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<INPUT class="fL" type="reset"/>
			<INPUT class="fR" type="submit"/>
		</FORM>
	</xsl:template>

</xsl:stylesheet>