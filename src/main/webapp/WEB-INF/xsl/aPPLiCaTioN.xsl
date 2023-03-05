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
			<STYLE type="text/css">.aPPLiCaTioN OL,.aPPLiCaTioN UL{margin:16px 0;padding-left:16px;list-style:inside}.aPPLiCaTioN B{font-weight:bold}.aPPLiCaTioN U{text-decoration:underline}.aPPLiCaTioN CAPTION{color:#C00;text-align:center}.aPPLiCaTioN .must:before{content:'*';color:#AB4025}.aPPLiCaTioN TH:after{content:'：'}.aPPLiCaTioN TH{text-align:right}</STYLE>
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
							<IMG alt="" src="{@contextPath}/IMG/application.png"/>
							<OL>
								<!--一-->
								<LI>
									<B>主辦單位：</B>
									<SPAN>苗栗產業創新推動中心</SPAN>
								</LI>
								<!--二-->
								<LI>
									<B>課程地點：</B>
									<SPAN>苗創中心201會議室(苗栗縣竹南鎮科研路36號2樓)</SPAN>
								</LI>
								<!--三-->
								<LI>
									<B>課程時間與概要：</B>
									<UL>
										<LI>
											<B>
												<U>課程名稱：</U>
											</B>
											<SPAN>「雲端物聯網數位服務-網路行銷」</SPAN>
											<OL>
												<LI>
													<U>時間：</U>
													<SPAN>104年09月25日(星期五)上午9時至12時，共計3小時。</SPAN>
												</LI>
												<LI>
													<U>大綱：</U>
													<SPAN>解析實體市場與虛擬市場的不同處、分析利弊，闡述網路行銷的觀念、重要性以及方法理論，最後導入如何藉由苗創中心資源，利⽤用平台執行網路行銷。</SPAN>
												</LI>
												<LI>
													<U>講師：</U>
													<SPAN>優家網張維駿副總經理、育達大學葉英斌副教授(暫定)。</SPAN>
												</LI>
												<LI>
													<U>議程：</U>
													<SPAN>網路行銷和實體世界市場之異同、行銷的實務和理論。</SPAN>
												</LI>
											</OL>
										</LI>
										<LI>
											<B>
												<U>課程名稱：</U>
											</B>
											<SPAN>「產業聚落與社群網站架構-聚落網上架教學」</SPAN>
											<OL>
												<LI>
													<U>時間：</U>
													<SPAN>104年10月02日(星期五)上午9時至12時，共計3小時。</SPAN>
												</LI>
												<LI>
													<U>大綱：</U>
													<SPAN>延伸網路行銷的觀念，教導廠商透過苗創中心資源，利用網路平台建立網站，為產品增加曝光率，串聯其他參與廠商，共同形成苗栗網路大商圈。</SPAN>
												</LI>
												<LI>
													<U>講師：</U>
													<SPAN>亞諾資訊何偉寬經理。</SPAN>
												</LI>
												<LI>
													<U>議程：</U>
													<SPAN>行銷網站製作教學。</SPAN>
												</LI>
											</OL>
										</LI>
										<LI>
											<B>
												<U>課程名稱：</U>
											</B>
											<SPAN>「數位服務網維運-資訊維護」</SPAN>
											<OL>
												<LI>
													<U>時間：</U>
													<SPAN>104年10月08日(星期四)上午9時至12時，共計3小時。</SPAN>
												</LI>
												<LI>
													<U>大綱：</U>
													<SPAN>闡述相關資訊維護理論，有關網路的金流、行銷法律與專利著作權等觀念，幫助廠商實行網路行銷時，可以保障自身權利。</SPAN>
												</LI>
												<LI>
													<U>講師：</U>
													<!--<SPAN>陳健瑞專案經理、林敏澤律師(暫定)。</SPAN>-->
													<SPAN>陳健瑞專案經理、法務講師(待定)。</SPAN>
												</LI>
												<LI>
													<U>議程：</U>
													<SPAN>網路金流操作和說明、網路行銷相關法律和專利著作權。</SPAN>
												</LI>
											</OL>
										</LI>
										<LI>
											<B>
												<U>課程名稱：</U>
											</B>
											<SPAN>「智慧樂活網路行銷名人與案例分享-個案及名人講座」</SPAN>
											<OL>
												<LI>
													<U>時間：</U>
													<SPAN>104年10月16日(星期五)上午9時至12時，共計3小時。</SPAN>
												</LI>
												<LI>
													<U>大綱：</U>
													<SPAN>請專家名人進行講座，以及參與廠商的成果、心得分享。</SPAN>
												</LI>
												<LI>
													<U>講師：</U>
													<SPAN>工研院服科中心羅國書副組長。</SPAN>
												</LI>
												<LI>
													<U>議程：</U>
													<SPAN>第三方支付。</SPAN>
												</LI>
											</OL>
										</LI>
									</UL>
								</LI>
								<!--四-->
								<LI>
									<B>報名方式：</B>
									<DIV>苗創網站(http://mii.itri.org.tw/)最新消息，有相關報名資訊及方法，請詳閱。</DIV>
									<UL>
										<LI>於網站線上報名。</LI>
										<LI>於網站「下載專區」下載「苗栗智慧樂活網路行銷學程報名表」，傳真或Email到苗創中心。<BR/>傳真:037-670092<BR/>Email:itri532659@itri.org.tw</LI>
									</UL>
								</LI>
								<!--五-->
								<LI>
									<B>經費規劃：</B>
									<SPAN>網路行銷系統建立、企業官網銜接、系統維運人員訓練，參與廠商負責企業網路系統建置(約200,000元)，整體系統初期建置由苗創中心統籌規劃建置，後續維運經費按使用者付費原則由參與廠商共同分攤。</SPAN>
								</LI>
								<!--六-->
								<LI>
									<B>繳費方式：</B>
									<SPAN>每人訓練費10,000元，(公司切結於網路銷售金額1%分期攤計)。</SPAN>
								</LI>
								<!--七-->
								<LI>
									<B>相關注意事項：</B>
									<UL>
										<LI>9/18完成報名、評審、確認資格，請詳閱報名資訊。</LI>
										<LI>苗創中心保有調整上課日期及講師變動之權利。</LI>
										<LI>主辦單位保留活動變更之權利，詳細活動資訊以本計畫網站公告為主。</LI>
										<LI>活動期間如遇颱風，且當地縣市政府公告停止上班，則延期辦理。</LI>
									</UL>
								</LI>
							</OL>
							<H6>網路行銷學程報名資料</H6>
							<FORM action="{form/@action}" method="POST">
								<TABLE width="100%" border="1" cellspacing="6" cellpadding="6">
									<CAPTION>
										<xsl:value-of select="form/@errorMessage"/>
									</CAPTION>
									<TR>
										<TH class="must">
											<LABEL for="name">公司名稱</LABEL>
										</TH>
										<TD>
											<INPUT id="name" type="text" name="name" value="{form/name}"/>
										</TD>
										<TH>
											<LABEL for="address">公司地址</LABEL>
										</TH>
										<TD>
											<INPUT id="address" type="text" name="address" value="{form/address}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">
											<LABEL for="phone">公司電話</LABEL>
										</TH>
										<TD>
											<INPUT id="phone" type="text" name="phone" value="{form/phone}"/>
										</TD>
										<TH>
											<LABEL for="fax">公司傳真</LABEL>
										</TH>
										<TD>
											<INPUT id="fax" type="text" name="fax" value="{form/fax}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="unifiedBusinessNumber">統一編號</LABEL>
										</TH>
										<TD>
											<INPUT id="unifiedBusinessNumber" type="text" name="unifiedBusinessNumber" value="{form/unifiedBusinessNumber}"/>
										</TD>
										<TH class="must">
											<LABEL for="fullname">姓名</LABEL>
										</TH>
										<TD>
											<INPUT id="fullname" type="text" name="fullname" value="{form/fullname}"/>
										</TD>
									</TR>
									<TR>
										<TH>
											<LABEL for="department">部門</LABEL>
										</TH>
										<TD>
											<INPUT id="department" type="text" name="department" value="{form/department}"/>
										</TD>
										<TH>
											<LABEL for="title">職稱</LABEL>
										</TH>
										<TD>
											<INPUT id="title" type="text" name="title" value="{form/title}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">
											<LABEL for="cellular">行動電話</LABEL>
										</TH>
										<TD>
											<INPUT id="cellular" type="text" name="cellular" value="{form/cellular}"/>
										</TD>
										<TH class="must">
											<LABEL for="email">Email</LABEL>
										</TH>
										<TD>
											<INPUT id="email" type="text" name="email" value="{form/email}"/>
										</TD>
									</TR>
									<TR>
										<TH class="must">便當</TH>
										<TD colspan="3">
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="false">
													<xsl:if test="form/vegetarian='false'">
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
											<LABEL>
												<INPUT type="radio" name="vegetarian" value="null">
													<xsl:if test="not(form/vegetarian='false')or not(form/vegetarian='true')">
														<xsl:attribute name="checked"/>
													</xsl:if>
												</INPUT>
												<SPAN>不用</SPAN>
											</LABEL>
										</TD>
									</TR>
									<TR>
										<TD class="c3" colspan="2">
										</TD>
									</TR>
								</TABLE>
								<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#FF2D21" type="submit" value="確認送出"/>
								<INPUT style="padding:3px 6px;border:0;float:right;color:#FFF;background-color:#D17F15" type="reset" value="取消重填"/>
								<BR style="float:none;clear:both" clear="all"/>
							</FORM>
							<P>由於場地限制，每間廠商僅限定一人報名。</P>
							<P>
								<DIV>建議參與課程資格條件：</DIV>
								<OL>
									<LI>檢附苗栗工商登記包含公司登記、商業登記或工廠登記等任一項。</LI>
									<LI>檢附登錄合格販售之商品報名請附相片及說明。</LI>
									<LI>檢附網站系統說明及維護人員履歷。</LI>
									<LI>具備網路行銷、雲端交易、第三方支付等系統設備及投資意願。</LI>
									<LI>以上申請廠商請檢附資料經學程委員會評審通過。</LI>
								</OL>
							</P>
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