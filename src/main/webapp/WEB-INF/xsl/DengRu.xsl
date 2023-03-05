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

	<xsl:include href="/welcome.xsl"/>

	<!--文件-->
	<xsl:template match="/">
		<HTML lang="zh-TW" dir="ltr">
			<xsl:apply-templates select="document"/>
		</HTML>
	</xsl:template>

	<!--根-->
	<xsl:template match="document">
		<HEAD>
			<META http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
			<META http-equiv="Content-Language" content="zh-TW"/>
			<META http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
			<LINK href="{@contextPath}/STYLE/default.css" rel="stylesheet" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/welcome.css" rel="stylesheet" type="text/css"/>
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<TITLE>後臺 &#187; 登入</TITLE>
		</HEAD>
		<BODY>
			<DIV id="container">
				<!--頭-->
				<xsl:apply-templates select="header">
					<xsl:with-param name="contextPath" select="@contextPath"/>
				</xsl:apply-templates>

				<!--橫幅-->
				<DIV id="banner">
					<IMG src="{@contextPath}/images/banner1.jpg" alt=""/>
					<IMG src="{@contextPath}/images/banner2.jpg" alt=""/>
					<IMG src="{@contextPath}/images/banner3.jpg" alt=""/>
				</DIV>

				<!--容器-->
				<DIV id="wrapper">
					<!--左側區塊之圖片連結-->
					<DIV id="aside">
						<UL>
							<xsl:for-each select="aside/*">
								<LI>
									<A href="{.}" title="{@title}">
										<IMG src="{../../@contextPath}/link/{@id}.png" alt="{@title}" height="52" width="176"/>
									</A>
								</LI>
							</xsl:for-each>
						</UL>
					</DIV>

					<!--左側區塊之計數器-->
					<DIV class="counter">
						<xsl:for-each select="counter/*">
							<SPAN>
								<xsl:value-of select="."/>
							</SPAN>
						</xsl:for-each>
					</DIV>

					<!--左側區塊之底下那張圖-->
					<DIV class="asidebg">
						<IMG src="{@contextPath}/IMG/qrCode.png" alt=""/>
					</DIV>

					<!--內容-->
					<DIV id="content">
						<xsl:apply-templates select="form"/>
					</DIV>

					<!--足-->
					<xsl:call-template name="footer">
						<xsl:with-param name="contextPath" select="@contextPath"/>
					</xsl:call-template>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

	<xsl:template match="form">
		<SPAN class="navigation">
			<A href="{@contextPath}/">網站管理</A>
			<SPAN> &#62; </SPAN>
			<A href="{@requestURI}">登入</A>
		</SPAN>
		<FORM style="margin-top:36px" action="{@requestURI}" method="POST">
			<FIELDSET>
				<TABLE id="form" width="100%">
					<CAPTION style="color:#C00;font-size:11px;text-align:center">
						<xsl:value-of select="errorMessage"/>
					</CAPTION>
					<TR>
						<TH style="text-align:right">
							<LABEL for="email">
								<SPAN style="color:#C00">*</SPAN>
								<SPAN>帳號：</SPAN>
							</LABEL>
						</TH>
						<TD>
							<INPUT id="email" style="padding:0 6px;border:1px dotted #9197A3;min-width:66%;height:24px;font-family:'bitstream vera sans mono','courier new',monospace" type="text" name="email" value="{email}"/>
						</TD>
					</TR>
					<TR>
						<TD colspan="2">
							<BR/>
						</TD>
					</TR>
					<TR>
						<TH style="text-align:right">
							<LABEL for="shadow">
								<SPAN style="color:#C00">*</SPAN>
								<SPAN>密碼：</SPAN>
							</LABEL>
						</TH>
						<TD>
							<INPUT id="shadow" style="padding:0 6px;border:1px dotted #9197A3;min-width:66%;height:24px;font-family:'bitstream vera sans mono','courier new',monospace" type="password" name="shadow"/>
						</TD>
					</TR>
					<TR>
						<TD colspan="2">
							<BR/>
						</TD>
					</TR>
					<TR>
						<TD colspan="2">
							<INPUT class="fL" style="padding:3px 6px;border:1px solid;border-color:#CCC #BBB #AAA;border-radius:3px;display:inline-block;line-height:16px;vertical-align:middle;color:#333;font-size:12px;text-decoration:none;cursor:pointer" type="reset" value="復原"/>
							<INPUT class="fR" style="padding:3px 6px;border:1px solid;border-color:#CCC #BBB #AAA;border-radius:3px;display:inline-block;line-height:16px;vertical-align:middle;color:#333;font-size:12px;text-decoration:none;cursor:pointer" type="submit" value="確定"/>
						</TD>
					</TR>
				</TABLE>
			</FIELDSET>
		</FORM>
	</xsl:template>

</xsl:stylesheet>