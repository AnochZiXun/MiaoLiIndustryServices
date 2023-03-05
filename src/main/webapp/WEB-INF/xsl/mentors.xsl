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
			<LINK href="{@contextPath}/STYLE/welcome.css" rel="stylesheet" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/downloads.css" rel="stylesheet" type="text/css"/>
			<LINK href="{@contextPath}/STYLE/mentors.css" rel="stylesheet" type="text/css"/>
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<TITLE>苗栗產業創新推動中心&#187;輔導團隊</TITLE>
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
									<A title="{@title}" href="{.}" target="_blank">
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
						<SPAN class="navigation">
							<A href="{@contextPath}/">首頁</A>
							<SPAN> &#62; </SPAN>
							<A>輔導團隊</A>
						</SPAN>
						<DIV class="tutorshipTeam">
							<TABLE class="download">
								<TR class="downloadtitle">
									<TD width="43">編號</TD>
									<TD width="115">單位</TD>
									<TD width="112">系/所/中心</TD>
									<TD width="66">姓名</TD>
									<TD width="71">職稱</TD>
									<TD width="274">專長</TD>
								</TR>
								<xsl:for-each select="content/*">
									<TR class="odd">
										<xsl:attribute name="class">
											<xsl:choose>
												<xsl:when test="position()mod'2'='0'">even</xsl:when>
												<xsl:otherwise>odd</xsl:otherwise>
											</xsl:choose>
										</xsl:attribute>
										<TD>
											<!--<xsl:value-of select="id"/>-->
											<xsl:value-of select="position()"/>
										</TD>
										<TD>
											<xsl:value-of select="institution"/>
										</TD>
										<TD>
											<xsl:value-of select="major"/>
										</TD>
										<TD>
											<xsl:value-of select="fullname"/>
										</TD>
										<TD>
											<xsl:value-of select="title"/>
										</TD>
										<TD class="textLeft">
											<xsl:value-of select="specialty" disable-output-escaping="yes"/>
										</TD>	
									</TR>
								</xsl:for-each>
							</TABLE>
						</DIV>
					</DIV>

					<!--足-->
					<xsl:call-template name="footer">
						<xsl:with-param name="contextPath" select="@contextPath"/>
					</xsl:call-template>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

</xsl:stylesheet>