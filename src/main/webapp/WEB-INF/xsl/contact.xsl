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
			<LINK href="{@contextPath}/STYLE/contact.css" rel="stylesheet" type="text/css"/>
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<TITLE>苗栗產業創新推動中心&#187;聯絡我們</TITLE>
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
							<A>聯絡我們</A>
						</SPAN>
						<DIV class="contact">
							<SPAN class="description">如您有任何需求或疑問，需要我們的團隊為您服務，請填寫下列表格留下您所需的服務內容與連絡方式，我們將有專人盡速與您連絡</SPAN>
							<FORM action="{form/@action}" method="POST">
								<TABLE class="contactForm">
									<CAPTION>
										<xsl:value-of select="form/@errorMessage"/>
									</CAPTION>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="company">您的公司名稱</LABEL>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT id="company" class="input" type="text" name="company" value="{form/company}"/>
										</TD>
									</TR>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="name">您的大名</LABEL>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT id="name" class="input" type="text" name="name" value="{form/name}"/>
										</TD>
									</TR>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="title">您的職稱</LABEL>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT id="title" class="input" type="text" name="title" value="{form/title}"/>
										</TD>
									</TR>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="phone">您的聯絡電話</LABEL>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT id="phone" class="input" type="text" name="phone" value="{form/phone}"/>
										</TD>
									</TR>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="email">您的Email</LABEL>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT id="email" class="input" type="text" name="email" value="{form/email}"/>
										</TD>
									</TR>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="address">您的地址</LABEL>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT id="address" class="input" type="text" name="address" value="{form/address}"/>
										</TD>
									</TR>
									<TR height="25">
										<TD class="c1"/>
										<TD class="c2">
											<LABEL for="contents">您想諮詢或發問的內容</LABEL>
										</TD>
									</TR>
									<TR height="270">
										<TD class="c3" colspan="2">
											<TEXTAREA id="contents" class="textarea" cols="1" name="content" rows="1">
												<xsl:value-of select="form/content"/>
											</TEXTAREA>
										</TD>
									</TR>
									<TR height="55">
										<TD class="c3" colspan="2">
											<INPUT class="submit" type="submit" value="送出"/>
										</TD>
									</TR>
								</TABLE>
							</FORM>
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