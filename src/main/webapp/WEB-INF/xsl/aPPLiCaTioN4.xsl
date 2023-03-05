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
			<STYLE type="text/css">.aPPLiCaTioN OL,.aPPLiCaTioN UL{margin:16px 0;padding-left:16px;list-style-position:inside}.aPPLiCaTioN B{font-weight:bold}.aPPLiCaTioN U{text-decoration:underline}.aPPLiCaTioN CAPTION{color:#C00;text-align:center}.aPPLiCaTioN .must:before{content:'*';color:#AB4025}.aPPLiCaTioN TH:after{content:'：'}.aPPLiCaTioN TH{text-align:right}</STYLE>
			<SCRIPT type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/{@jQueryVersion}/jquery.min.js"/>
			<SCRIPT type="text/javascript" src="{@contextPath}/SCRIPT/welcome.js"/>
			<TITLE>苗栗產業創新推動中心&#187;活動報名</TITLE>
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
						<DIV class="aPPLiCaTioN">
							<h1 style="text-align: center;">研發創新計畫書撰寫技巧與實務應用實戰班 - 計畫書1對1業師輔導</h1>
							<ul>
								<li>上課日期：4/11~12‭ ‬09:00~12:00、13:00~17:00‭<br/><span style="color:#0070c0">參加課程之廠商與業師，當日上課時請準備預申請苗栗縣地方型SBIR計畫之計畫書初稿，以利上課時與老師討論。</span>‭(‬計畫書報告時間10分鐘，10分鐘講評，每家公司限20分鐘‭)‬</li>
								<li>上課地點：苗栗產業創新推動中心2F<br/>(‬苗栗縣竹南鎮科研路36號1樓201室‭)‬</li>
								<li>各廠商業者上課時程表‭:‬</li>
							</ul>
							<table border="1" cellpadding="3" cellspacing="3">
								<caption>
									<p style="text-align: left;">
										<span style="color:#0000FF">1‭.‬：04/11課程安排</span>
									</p>
								</caption>
								<tbody>
									<tr>
										<td style="text-align: center;">編號</td>
										<td style="text-align: center;">上課時段</td>
										<td style="text-align: center;">參加廠商‭/‬業師</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">1</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬09:00~10:00</span>
										</td>
										<td>1迅展股份有限公司‭/‬陳昌居<br/>2宥兒樂服務有限公司‭/‬白乃遠、黃家洋<br/>3野桐工坊色舞繞‭/‬張世明</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">2</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬10:00~11‭:‬00</span>
										</td>
										<td>1兆農科技‭/‬陳弘正<br/>2翔茂國際開發股份有限公司‭/‬江素貞<br/>3張張精彩文創設計有限公司‭/‬張宏淵</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">3</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬11:00~12:00</span>
										</td>
										<td>1華仕德科技股份有限公司‭/‬蔡文能<br/>2春田窯‭/‬詹瓊瑩<br/>3銅鑼窯‭/‬詹瓊瑩</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">4</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬13:00~14‭:‬00</span>
										</td>
										<td>1晶呈科技‭/‬呂春福<br/>2世鎧精密股份有限公司<br/>3丞揚運動工程有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">5</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬14:00~15‭:‬00</span>
										</td>
										<td>1納姆內股份有限公司<br/>2台灣鄉村酒業有限公司<br/>3承化實業股份有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">6</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬15:00~16:00</span>
										</td>
										<td>1欣澔科技有限公司<br/>2公館鄉農會<br/>3聖堂數位有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">7</span>
										</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/11‭ ‬16:00~17‭:‬00</span>
										</td>
										<td>1東山製材所<br/>2逵展實業有限公司<br/>3怡明茶園</td>
									</tr>
								</tbody>
							</table>
							<table border="1" cellpadding="3" cellspacing="3">
								<caption>
									<p style="text-align: left;">
										<span style="color:#0000FF">2.：04/12 課程安排</span>
									</p>
								</caption>
								<tbody>
									<tr>
										<td style="text-align: center;">編號</td>
										<td style="text-align: center;">上課時段</td>
										<td style="text-align: center;">參加廠商‭/‬業師</td>
									</tr>
									<tr>
										<td style="text-align: center;">1</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/12‭ ‬09:00~10:00</span>
										</td>
										<td>1有限責任苗栗縣油甘運銷合作社<br/>2玨鼎生物科技有限公司<br/>3青志金屬工業股份有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">2</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/12‭ ‬10:00~11‭:‬00</span>
										</td>
										<td>1耕山文創‭/‬鄭琇君<br/>2杜石地一號‭/‬鄭琇君<br/>3釣魚的貓‭/‬鄭琇君</td>
									</tr>
									<tr>
										<td style="text-align: center;">3</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/12‭ ‬11:00~12‭:‬00</span>
										</td>
										<td>1恆智重機‭/‬鄭琇君<br/>2無雙設計國際有限公司<br/>3力有興業股份有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">4</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/12‭ ‬13:00~14:00</span>
										</td>
										<td>1加賀金屬工業股份有限公司<br/>2台宙晶體科技股公司<br/>3謙淳有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">5</td>
										<td style="text-align: center;">
											<span style="font-family:courier new,courier,monospace">04/12‭ ‬14:00~15‭:‬00</span>
										</td>
										<td>1愛之璇能源科技公司<br/>2賀喜能源股份有限公司<br/>3虹銘股份有限公司</td>
									</tr>
									<tr>
										<td style="text-align: center;">6</td>
										<td style="font-family:courier new,courier,monospacetext-align: center;">
											<span style="font-family:courier new,courier,monospace">04/12‭ ‬15:00~16‭:‬00</span>
										</td>
										<td>1六友汽車<br/>2钲凱機電公司</td>
									</tr>
								</tbody>
							</table>
							<p>報名網址：http://mii.itri.org.tw/application.asp<br/>苗創中心電話：037-670050<br/>苗創中心傳真：037-670090<br/>苗創中心email：mii@itri.org.tw</p>
							<BR/>
							<FORM action="{form/@action}" method="POST">
								<TABLE width="100%" border="1" cellspacing="6" cellpadding="6">
									<CAPTION>
										<xsl:value-of select="form/@errorMessage"/>
									</CAPTION>
									<TR>
										<TH>
											<LABEL for="organization">單位名稱</LABEL>
										</TH>
										<TD colspan="3">
											<INPUT id="organization" type="text" name="organization" value="{form/organization}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">
											<LABEL for="fullname">姓名</LABEL>
										</TH>
										<TD>
											<INPUT id="fullname" type="text" name="fullname" value="{form/fullname}"/>
										</TD>
										<TH>
											<LABEL for="title">職稱</LABEL>
										</TH>
										<TD>
											<INPUT id="title" type="text" name="title" value="{form/title}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="phone">連絡電話</LABEL>
										</TH>
										<TD>
											<INPUT id="phone" type="text" name="phone" value="{form/phone}"/>
										</TD>
										<TH class="must">
											<LABEL for="email">電子郵件</LABEL>
										</TH>
										<TD>
											<INPUT id="email" type="text" name="email" value="{form/email}"/>
										</TD>
									</TR>
									<!--
									<TR>
										<TH class="must">便當</TH>
										<TD colspan="3">
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="false">
													<xsl:if test="not(form/vegetarian='true')">
														<xsl:attribute name="checked"/>
													</xsl:if>
												</INPUT>
												<SPAN>葷</SPAN>
											</LABEL>
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="true">
													<xsl:if test="form/vegetarian='true'">
														<xsl:attribute name="checked"/>
													</xsl:if>
												</INPUT>
												<SPAN>素</SPAN>
											</LABEL>
										</TD>
									</TR>
									-->
									<TR>
										<TD colspan="4">
											<INPUT style="padding:3px 6px;border:0;float:left;color:#FFF;background-color:#D17F15" type="reset" value="取消重填"/>
											<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#FF2D21" type="submit" value="確認送出"/>
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