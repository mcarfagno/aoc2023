import networkx as nx
import re
import matplotlib.pyplot as plt
from functools import reduce

file = "../input/day25.txt"

edges = [e for e in open(file).readlines()]
edges = [re.findall(r"([a-z]+)+", e) for e in edges]
edges = list(map(lambda e: [(e[0], e[i]) for i in range(1, len(e))], edges))
edges = [x for xs in edges for x in xs]  # flatten

# print(edges)
G = nx.Graph()
G.add_edges_from(edges)

# find the 3 edges
# nx.draw(G, with_labels=True, font_weight="bold")
# plt.show()

# delete 3 edges
# G.remove_edge("cmg", "bvb")
# G.remove_edge("nvd", "jqt")
# G.remove_edge("hfx", "pzl")
G.remove_edge("nct", "kdk")
G.remove_edge("tvj", "cvx")
G.remove_edge("fsv", "spx")

# subgraphs
S = [G.subgraph(c).copy() for c in nx.connected_components(G)]
# part1
print(reduce(lambda x, y: x * y, [g.number_of_nodes() for g in S]))

# subax1 = plt.subplot(121)
# nx.draw(S[0], with_labels=True, font_weight="bold")
# subax2 = plt.subplot(122)
# nx.draw(S[1], with_labels=True, font_weight="bold")
# plt.show()
