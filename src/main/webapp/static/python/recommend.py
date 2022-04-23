# import warnings filter
from warnings import simplefilter
# ignore all future warnings
simplefilter(action='ignore', category=FutureWarning)
from sklearn.feature_extraction.text import TfidfVectorizer
d = ("The sky is blue","The sun is bright","The sun in the sky is bright","we can see the shining sun, the bright sun")
vector = TfidfVectorizer()
matrix = vector.fit_transform(d)
print(vector.get_feature_names())
