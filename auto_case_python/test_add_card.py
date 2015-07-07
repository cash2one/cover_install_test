#-*- coding: UTF-8 -*-
'''
create on 20150619
@author hwu
'''
import sys
reload(sys)
sys.setdefaultencoding("utf-8")
import os
import time
import datetime
import unittest
import SimpleXmlParser
from time import sleep
import json
from appium import webdriver
from selenium.webdriver.common.touch_actions import TouchActions

CONFPATH = './auto_case_python/test_device.xml'
TIME=2
maxTime=TIME
oldy=800
newy=200
PATH = lambda p: os.path.abspath(p)
print os.path.abspath('.')

class CardTest(unittest.TestCase):
	def setUp(self):
		parser = SimpleXmlParser.SimpleXmlParser(CONFPATH)
		info = parser.parse(["app", "appPackage", "appActivity", "platformName", "platformVersion", "deviceName","automationName"])
		info['app'] = os.path.abspath(
		        #os.path.join(os.path.abspath('.'), 'old_release'+ os.path.sep + info['app']))
		        os.path.join(os.path.abspath('.'), 'old_release'+ os.path.sep + sys.argv[1] + '_thrunk_all_com.baidu.searchbox.apk'))
		print info['app']
		output = json.dumps(info)
		desired_caps = json.loads(output)
		print desired_caps
		self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)
		
	def tearDown(self):
		self.driver.quit()
		        								   
	def __init(self):
		self.driver.keyevent(4)
		maxTime=5
		while maxTime:
			try:
				closeElment=self.driver.find_element_by_id('close')
				if closeElment:
					closeElment.click()
					break
				maxTime-=1
			except:
				sleep(1)
				maxTime-=1								   
		sleep(3)
		
	def __init2(self):	
		self.driver.keyevent(4)
		maxTime=5
		while maxTime:
			try:
				laterElement=self.driver.find_element_by_link_text(u'稍后')	
				if laterElement:
					laterElement.click()
					break				
				maxTime-=1
			except:
				sleep(1)
				maxTime-=1								   
		sleep(3)	
		
	def screenshot(self):
		path = PATH(os.getcwd() + "/output/pic")
		timestamp = time.strftime('%Y-%m-%d-%H-%M-%S',time.localtime(time.time()))
		os.popen("adb wait-for-device")
		os.popen("adb shell screencap -p /data/local/tmp/tmp.png")
		if not os.path.isdir(PATH(os.getcwd() + "/output/pic")):
			os.makedirs(path)
			os.popen("adb pull /data/local/tmp/tmp.png " + PATH(path + "/" + timestamp + ".png"))
			os.popen("adb shell rm /data/local/tmp/tmp.png")
			print "success"
	
	#param name link_text
	#click link_text		
	def myfind_element_by_link_text(self,name):
		maxTime=TIME
		while maxTime:
			try:
				temp = self.driver.find_element_by_link_text(name)
				if temp:
					temp.click()
					break
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')	
	
	#param name class_name
	#return element
	def myfind_elements_by_class_name(self,name):
		maxTime=TIME
		while maxTime:
			try:
				temp = self.driver.find_elements_by_class_name(name)
				if temp:
					return temp
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
		
	#param name link_text
	#param num the numTh link_text
	#click link_text
	def myfind_elements_by_link_text(self,name,num):
		maxTime=TIME
		while maxTime:
			try:
				temp = self.driver.find_elements_by_link_text(name)
				if temp:
					temp[num].click()
					break
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')	
	#param name link_text
	#return Boolean
	def myfind_element_by_link_text_not_click(self,name):
		maxTime=TIME
		while maxTime:
			try:
				temp = self.driver.find_element_by_link_text(name)
				if temp:
					return True
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')	
		
	#param id
	#click element
	def myfind_element_by_id(self,id):
		maxTime=TIME
		while maxTime:
			try:
				temp = self.driver.find_element_by_id(id)
				if temp:
					temp.click()
					break
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')
	
	#param id
	#param keys send_text
	#send_keys
	def myfind_element_by_id_sendKeys(self,id,keys):
		maxTime=TIME
		while maxTime:
			try:
				temp = self.driver.find_element_by_id(id)
				if temp:
					temp.send_keys(keys)
					break
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',id
			self.screenshot()
			raise Exception('not found,please up MaxTime')

	#name xpath
	#click element
	def myfind_element_by_xpath(self,name):
		maxTime=TIME
		while maxTime:
			try:
				temp  = self.driver.find_element_by_xpath(name)
				if temp:
					temp.click()
					break
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')

	#param name xpath
	#param num the numTh xpath
	def myfind_elements_by_xpath(self,name,num):
		maxTime=TIME
		while maxTime:
			try:
				temp  = self.driver.find_elements_by_xpath(name)
				if temp:
					temp[num].click()
					break
				maxTime-=1
			except:
				maxTime-=1
				sleep(2)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')
		
	#
	def myfind_element_by_xpath_not_click(self,name):
		maxTime=TIME
		while maxTime:
			try:
				temp  = self.driver.find_element_by_xpath(name)
				if temp:
					return True
				maxTime-=1
			except:
				maxTime-=1
				sleep(1)
		if maxTime==0:
			print 'not found:',name
			self.screenshot()
			raise Exception('not found,please up MaxTime')
		
	def switch_to_context_WEB_0(self):
		self.driver.switch_to.context('WEBVIEW_0')
		sleep(3)
	def switch_to_context_NATIVE_APP(self):
		self.driver.switch_to.context('NATIVE_APP')
		sleep(3)
	def switch_to_context_WEB_1(self):
		self.driver.switch_to.context('WEBVIEW_1')
		sleep(3)
	
	#版本 4.0 
	def test_card_case1(self):
		self.__init2()
		#点击 “关注”
		self.myfind_element_by_link_text(u'关注')		
		#点击 “+”
	        self.myfind_elements_by_class_name('android.widget.ImageView')[1].click()  
		sleep(1)		
		self.switch_to_context_WEB_0()		
		#点击 股票模块的 搜索添加
		self.myfind_elements_by_link_text(u"搜索添加", 1)
		sleep(1)		
		self.switch_to_context_NATIVE_APP()
		#文本框 输入百度股票
		self.myfind_elements_by_class_name("android.widget.EditText")[0].send_keys(u'百度股票')
		self.myfind_element_by_link_text(u"搜索")
		sleep(3)
		print "current_url:", self.driver.current_url
		#self.switch_to_context_WEB_0()
		print "current_url:", self.driver.current_url
		self.myfind_element_by_link_text(u"关注")
		#self.myfind_elements_by_class_name('android.widget.ImageView')[0].click()
		sleep(3)
		
		
		#self.switch_to_context_WEB_1()
		#print "current_url:", self.driver.current_url
		#print "contexts:", self.driver.contexts
		#print "current_context:", self.driver.current_context
		#sleep(3)
		#self.myfind_element_by_xpath('//li[@class="stock"]')
		#print "current_url:", self.driver.current_url
		#print "contexts:", self.driver.contexts
		#print "current_context:", self.driver.current_context
		#sleep(3)
		#self.myfind_element_by_xpath('//a[@class="btn-add-card"]')
		#print "current_url:", self.driver.current_url
		#print "contexts:", self.driver.contexts
		#print "current_context:", self.driver.current_context
		#sleep(3)
		#self.myfind_element_by_id_sendKeys('J-search-input', u'百度股票')
		#self.myfind_element_by_id('J-search-add')
		#sleep(3)	
		
	#版本 5.0-6.0	
	def test_card_case2(self):
		self.__init()
		#5.5会弹出“拍一拍，你就知道”
		self.driver.keyevent(4)
		self.myfind_element_by_link_text(u'添加卡片')
		self.myfind_element_by_link_text(u'添加卡片')				
		self.switch_to_context_WEB_1()
		sleep(3)
		self.myfind_element_by_xpath('//li[@class="stock"]')
		sleep(3)
		self.myfind_element_by_xpath('//a[@class="btn-add-card"]')
		sleep(3)
		self.myfind_element_by_id_sendKeys('J-search-input', u'百度股票')
		self.myfind_element_by_id('J-search-add')
		sleep(3)
		
	#版本 6.1,6.2,6.3,6.4,6.5
	def test_card_case3(self):
		self.__init()		
		self.myfind_element_by_link_text(u'添加卡片')				
		self.switch_to_context_WEB_0()
		sleep(3)
		self.myfind_element_by_xpath('//li[@class="stock"]')
		sleep(3)
		self.myfind_element_by_xpath('//a[@class="btn-add-card"]')
		sleep(3)
		self.myfind_element_by_id_sendKeys('J-search-input', u'百度股票')
		self.myfind_element_by_id('J-search-add')
	sleep(3)	
		
if __name__ == '__main__':
	#定义一个测试单元容器
	suite = unittest.TestSuite()
	#版本号
	version = float(sys.argv[1])
	if version < 5.0:
		suite.addTest(CardTest("test_card_case1"))
	elif version <= 6.0:
		suite.addTest(CardTest("test_card_case2"))
	else:
		suite.addTest(CardTest("test_card_case3"))
	
	unittest.TextTestRunner(verbosity=2).run(suite)
		