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
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<TITLE>苗栗產業創新推動中心&#187;輔導案例</TITLE>
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
							<A href="{@contextPath}/counselings.asp">輔導案例</A>
							<SPAN> &#62; </SPAN>
							<SPAN>微型產業技術輔導計畫</SPAN>
						</SPAN>
						<DIV id="tutorship">
							<H3>微型產業技術輔導計畫</H3>
							<DIV class="tutorshipList">
								<UL>
									<LI>
										<SPAN class="title">計畫名稱</SPAN>
										<SPAN class="description">
											<xsl:value-of select="content/name"/>
										</SPAN>
									</LI>
									<LI>
										<SPAN class="title">受輔導公司</SPAN>
										<SPAN class="description">
											<xsl:value-of select="content/vendorName"/>
										</SPAN>
									</LI>
									<LI>
										<SPAN class="title">公司住址</SPAN>
										<SPAN class="description">
											<xsl:value-of select="concat(content/districtName,content/vendorAddress)"/>
										</SPAN>
									</LI>
									<LI>
										<SPAN class="title">聯絡方式</SPAN>
										<SPAN class="description">
											<xsl:value-of select="content/vendorContactNumber"/>
										</SPAN>
									</LI>
									<!--
									<LI>
										<SPAN class="title">補助金額</SPAN>
										<SPAN class="description">
											<xsl:value-of select="format-number(content/amount,'###,###')"/>
										</SPAN>
									</LI>
									-->
									<LI>
										<SPAN class="title">聯絡人</SPAN>
										<SPAN class="description">
											<xsl:value-of select="content/vendorContactName"/>
										</SPAN>
									</LI>
									<LI>
										<SPAN class="title">業者現況</SPAN>
										<SPAN class="description" style="line-height:32px !important">
											<xsl:value-of select="content/condition"/>
										</SPAN>
									</LI>
									<LI>
										<SPAN class="title">輔導項目</SPAN>
										<SPAN class="description" style="line-height:32px !important">
											<xsl:value-of select="content/items"/>
										</SPAN>
									</LI>
									<LI>
										<xsl:if test="content/photo='false'">
											<xsl:attribute name="class">no</xsl:attribute>
										</xsl:if>
										<SPAN class="title">輔導效益</SPAN>
										<SPAN class="description" style="line-height:32px !important">
											<xsl:value-of select="content/performance"/>
										</SPAN>
									</LI>
									<xsl:if test="content/photo='true'">
										<LI class="no">
											<SPAN class="title">相關照片</SPAN>
											<SPAN class="description img">
												<IMG src="{@contextPath}/counseling/{content/@id}/photo.png" alt="相關照片" width="580"/>
											</SPAN>
										</LI>
									</xsl:if>
								</UL>
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