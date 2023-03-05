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
	
	<!--<xsl:import href="/welcome.xsl"/>-->
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
			<LINK href="{@contextPath}/STYLE/events.css" rel="stylesheet" type="text/css"/>
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/events.js"/>
			<TITLE>苗栗產業創新推動中心&#187;活動花絮</TITLE>
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
							<A>活動花絮</A>
						</SPAN>
						<DIV class="photograph">
							<UL>
								<xsl:for-each select="content/*">
									<xsl:sort select="sort" data-type="number"/>
									<xsl:comment>
										<xsl:value-of select="sort"/>
									</xsl:comment>
									<LI>
										<SPAN>
											<xsl:attribute name="class">
												<xsl:choose>
													<xsl:when test="position()mod'2'='0'">img even</xsl:when>
													<xsl:otherwise>img</xsl:otherwise>
												</xsl:choose>
											</xsl:attribute>
											<A href="{@contextPath}/event/{@id}/">
												<IMG src="{@contextPath}/event/{@id}.png" alt="" height="175" width="260"/>
											</A>
										</SPAN>
										<SPAN>
											<xsl:attribute name="class">
												<xsl:choose>
													<xsl:when test="position()mod'2'='0'">text textRight</xsl:when>
													<xsl:otherwise>text</xsl:otherwise>
												</xsl:choose>
											</xsl:attribute>
											<A href="{@contextPath}/event/{@id}/">
												<P>
													<xsl:value-of select="since"/>
												</P>
												<H4>
													<xsl:value-of select="title"/>
												</H4>
											</A>
										</SPAN>
									</LI>
								</xsl:for-each>
							</UL>
						</DIV>
						<DIV class="cF">
							<xsl:if test="pagination/@previous">
								<A class="bUttOn fL" href="events.asp?p={pagination/@previous}">&#xF0D9;</A>
							</xsl:if>
							<xsl:if test="pagination/@next">
								<A class="bUttOn fR" href="events.asp?p={pagination/@next}">&#xF0DA;</A>
							</xsl:if>
							<DIV class="cF" style="text-align:center">
								<FORM action="events.asp" method="GET">
									<SPAN>第</SPAN>
									<SELECT name="p">
										<xsl:for-each select="pagination/option">
											<OPTION value="{@value}">
												<xsl:if test="@selected">
													<xsl:attribute name="selected"/>
												</xsl:if>
												<xsl:value-of select="." disable-output-escaping="yes"/>
											</OPTION>
										</xsl:for-each>
									</SELECT>
									<SPAN>&#47;</SPAN>
									<SPAN>
										<xsl:value-of select="pagination/@totalPages"/>
									</SPAN>
									<SPAN>頁</SPAN>
								</FORM>
							</DIV>
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