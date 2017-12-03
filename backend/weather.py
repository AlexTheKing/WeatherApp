from bs4 import BeautifulSoup
import requests
from flask import Flask
from flask import request
import json

site = 'http://pogoda.by'
app = Flask('Weather')

def getCitiesWithUrls():
	req = requests.get(site)
	soup = BeautifulSoup(req.text, 'html.parser')
	return {x.next_element.text:site+x.next_element['href'] for x in soup.find_all('td', 'sr')}

def prettifyDay(day):	
	l = day.split('.')
	date = l[1]
	time = l[0].split(', ')
	time.reverse()
	return '{0}, {1}'.format(date, ', '.join(time))

class Day:
        def __init__(self, name, info):
                self.name = name
                self.temp = info[0]
                self.description = info[1]
                self.wind = info[2]
                self.humidity = info[3]
                self.pressure = info[4]

def getDescription(tableRows):
        unformatted = [list(x.children)[1]['title'] for x in list(tableRows[1].children)[4:-1:2]]
        return ['. '.join(el.split('.')) for el in unformatted]

def getTemp(tableRows):
        return ['{0} °С'.format(x.text) for x in list(tableRows[3].children)[4:-1:2]]

def getWind(tableRows):
        return [x.text[1:-1] for x in list(tableRows[5].children)[4:-1:2]]

def getPressure(tableRows):
        return [('{0} гПа'.format(x.next_element), '{0} мм рт. ст.'.format(x.next_element.next_element.next_element.text)) for x in list(tableRows[7].children)[4:-1:2]]

def getHumidity(tableRows):
        return ['{0}%'.format(x.text) for x in list(tableRows[9].children)[4:-1:2]]        
        
@app.route('/cities')
def getCities():
	return json.dumps(list(getCitiesWithUrls().keys()))

@app.route('/weather')
def getWeather():
        city = request.args.get('city')
        url = getCitiesWithUrls()[city]
        req = requests.get(url)
        soup = BeautifulSoup(req.text, 'html.parser')
        days = [prettifyDay(x.text) for x in soup.find_all('td', 'dat')]
        tableRows = list(soup.find('td','dat').parent.next_siblings)
        description = getDescription(tableRows)
        temp = getTemp(tableRows)
        wind = getWind(tableRows)
        pressure = getPressure(tableRows)
        humidity = getHumidity(tableRows)
        info = zip(temp, description, wind, humidity, pressure)
        return json.dumps([Day(name, el).__dict__ for name, el in zip(days, info)])

if __name__ == '__main__':
	app.run(host='0.0.0.0')

	
