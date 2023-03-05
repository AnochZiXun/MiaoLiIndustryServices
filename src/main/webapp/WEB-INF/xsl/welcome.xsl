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
			<TITLE>苗栗產業創新推動中心</TITLE>
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
					<DIV class="counter" title="今日瀏覽次數">
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
						<!--活動花絮-->
						<DIV class="activity">
							<SPAN class="title">
								<H3>活動花絮</H3>
								<A href="{@contextPath}/events.asp">more &#62;</A>
							</SPAN>
							<SPAN class="activityList">
								<UL>
									<xsl:for-each select="events/*">
										<LI>
											<SPAN class="img">
												<A href="{@contextPath}/event/{@id}/">
													<IMG src="{@contextPath}/event/{@id}.png" alt="" height="106" width="152"/>
												</A>
											</SPAN>
											<A href="{@contextPath}/event/{@id}/">
												<xsl:value-of select="."/>
											</A>
										</LI>
									</xsl:for-each>
								</UL>
							</SPAN>
						</DIV>
						
						<!--最新消息-->
						<DIV class="indexNews">
							<SPAN class="title">
								<H3>最新消息</H3>
								<A href="{@contextPath}/newses.asp">more &#62;</A>
							</SPAN>
							<SPAN class="indexNewsList">
								<OL id="events">
									<xsl:for-each select="newses/*">
										<LI>
											<SPAN>
												<xsl:value-of select="@createdOn"/>
											</SPAN>
											<A href="{@contextPath}/news/{@id}/">
												<xsl:value-of select="."/>
											</A>
										</LI>
									</xsl:for-each>
								</OL>
							</SPAN>
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

	<!--頭-->
	<xsl:template match="header">
		<xsl:param name="contextPath"/>
		<DIV id="header">
			<DIV id="logo">
				<H1>
					<A href="{$contextPath}/" title="首頁">
						<IMG src="{$contextPath}/images/logo.png" alt="瞜狗"/>
					</A>
				</H1>
			</DIV>
			<DIV class="topnav">
				<UL>
					<LI>
						<A href="{$contextPath}/">首頁</A>
					</LI>
					<LI>
						<A href="{$contextPath}/sitemap.asp">網站導覽</A>
					</LI>
					<LI>
						<A href="{$contextPath}/HouTai.asp">網管A</A>
					</LI>
					<LI>
						<A href="http://form.miiitri.com/" target="_blank">訪視診斷電子表單</A>
					</LI>
				</UL>
			</DIV>
			<DIV class="search">
				<FORM id="searchByGoogle" action="https://www.google.com/search" method="GET">
					<INPUT name="q" type="hidden"/>
					<INPUT id="query" class="input" ytpe="text"/>
					<INPUT name="ie" type="hidden" value="utf-8"/>
					<INPUT name="oe" type="hidden" value="utf-8"/>
					<INPUT class="searchSend" type="image" src="{$contextPath}/images/searchbtn.png"/>
				</FORM>
			</DIV>
			<DIV id="menu">
				<UL>
					<LI>
						<H2>
							<A title="計畫簡介" href="{$contextPath}/introduction.asp">
								<xsl:if test="@on='計畫簡介'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>計畫簡介</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="最新消息" href="{$contextPath}/newses.asp">
								<xsl:if test="@on='最新消息'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>最新消息</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="活動公告" style="color:#AB4025" href="javascript:void(0)">
							<!--<A title="活動公告" style="color:#AB4025" href="{$contextPath}/application.asp">-->
							<!--<A title="活動公告" style="color:#AB4025" href="http://goo.gl/forms/YBEM3x6DmvAKbiCU2" target="_blank">-->
								<xsl:if test="@on='活動公告'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>活動公告</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="活動照片" href="{$contextPath}/events.asp">
								<xsl:if test="@on='活動照片'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>活動照片</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="輔導流程" href="{$contextPath}/flows.asp">
								<xsl:if test="@on='輔導流程'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>輔導流程</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="輔導團隊" href="{$contextPath}/mentors.asp">
								<xsl:if test="@on='輔導團隊'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>輔導團隊</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="下載專區" href="{$contextPath}/downloads.asp">
								<xsl:if test="@on='下載專區'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>下載專區</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="輔導案例" href="{$contextPath}/counselings.asp">
								<xsl:if test="@on='輔導案例'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>輔導案例</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="友站連結" href="{$contextPath}/links.asp">
								<xsl:if test="@on='友站連結'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>友站連結</SPAN>
							</A>
						</H2>
					</LI>
					<LI>
						<H2>
							<A title="聯絡我們" href="{$contextPath}/contact.asp">
								<xsl:if test="@on='聯絡我們'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>聯絡我們</SPAN>
							</A>
						</H2>
					</LI>
					<LI class="no">
						<!--
						<H2>
							<A title="產業聚落" href="http://www.mii-itri.org/" target="_blank">
								<xsl:if test="@on='產業聚落'">
									<xsl:attribute name="class">on</xsl:attribute>
								</xsl:if>
								<SPAN>產業聚落</SPAN>
							</A>
						</H2>
						-->
						<H2>
							<A title="產業聚落" href="http://www.mii-itri.org/" target="_blank">
								<SPAN>產業聚落</SPAN>
							</A>
						</H2>
						<UL>
							<LI>
								<A href="http://mii-itri.org/" target="_blank">產業聚落</A>
							</LI>
							<LI>
								<A href="http://g1.joymall.com.tw/" target="_blank">聯合大學</A>
							</LI>
							<LI>
								<A href="http://g2.joymall.com.tw/" target="_blank">亞太科大</A>
							</LI>
							<LI>
								<A href="http://g3.joymall.com.tw/" target="_blank">育達科大</A>
							</LI>
						</UL>
					</LI>
				</UL>
			</DIV>
		</DIV>
	</xsl:template>

	<!--足-->
	<xsl:template name="footer">
		<xsl:param name="contextPath"/>
		<DIV id="footer">
			<P class="companyMsg">苗栗產業創新推動中心：苗栗縣竹南鎮科研路36號&#160;Tel：037&#45;670050(代表號)&#47;037&#45;670049&#160;Fax：037&#45;670092</P>
			<DIV class="copyright">
				<A href="{$contextPath}/">隱私權及網站安全政策</A>
				<SPAN>&#160;&#124;&#160;</SPAN>
				<A href="{$contextPath}/">著作權聲明</A>
				<SPAN>&#160;&#124;&#160;</SPAN>
				<SPAN>建議瀏覽解析度：1024&#215;768</SPAN>
			</DIV>
		</DIV>
	</xsl:template>

</xsl:stylesheet>