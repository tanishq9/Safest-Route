import pandas as pd
import pickle
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics.pairwise import euclidean_distances
import sys

class WeightOfPath:
    def __init__(self, weight_path = "model.pickle"):
        self.kmeans = pickle.load(open(weight_path, "rb"))

    def pathSum(self, route):
        pred = self.kmeans.predict(route)
        pred = self.kmeans.cluster_centers_[pred]
        return np.sqrt(np.sum((pred - route) ** 2, axis=1))

def main():
    routes = sys.argv[1:]
    #print(routes)
    #route = ['77.233278,28.666487', '77.232199,28.668742', '77.229976,28.676197', '77.233886,28.667097', '77.239048,28.662151', '77.252168,28.650419', '77.255688,28.645179', '77.247154,28.634789', '77.247065,28.6324', '77.246911,28.630731', '77.24704,28.625575', '77.25024,28.615336', '77.254689,28.60141', '77.256378,28.596187', '77.264442,28.582857', '77.263546,28.57585', '77.26098,28.574218', '77.258763,28.572684', '77.283446,28.538613', '77.286808,28.532689', '77.298333,28.511609', '77.299774,28.502367', '77.305478,28.477684', '77.305773,28.475012', '77.309398,28.430756', '77.310748,28.416068', '77.316682,28.415944', '77.317265,28.408842', '77.317802,28.408881']
    for route in routes:
        #print(route)
        route_list = list(map(float, route.split(",")))
        route_list = np.reshape(route_list, (len(route_list)//2, 2))
        #print(route_list)
        w = WeightOfPath()
        print(np.exp(-np.sum(w.pathSum(route_list))/2000))

if __name__ ==  "__main__":
    main()
