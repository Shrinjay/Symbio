import bs4 as bs
import requests


# Goal: Implement the builder pattern of sorts, that allows a raw piece of data to be loaded into another,
# programmable data format

def run_extractor(source):

    html = get_html(source)
    bs_obj = get_bs_object(html)
    
    return bs_obj


def get_html(link):
    return requests.get(link)


def get_bs_object(html):
    return bs.BeautifulSoup(html, 'html.parser')
