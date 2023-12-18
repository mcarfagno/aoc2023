import heapq
from enum import Enum


direction = {
    ">": (+1, 0),
    "<": (-1, 0),
    "^": (0, -1),
    "v": (0, +1),
}

turns = {
    ">": (">", "v", "^"),
    "<": ("<", "v", "^"),
    "^": ("<", ">", "^"),
    "v": ("<", "v", ">"),
}


class WeightedGrid:
    def __init__(self, grid):
        self.height = len(grid)
        self.width = len(grid[0])
        self.weights = {}
        for y in range(self.height):
            for x in range(self.width):
                self.weights[(x, y)] = int(grid[y][x])

    def cost(self, from_node, to_node):
        (x, y, _, _) = to_node
        return self.weights.get((x, y))

    def in_bounds(self, id):
        (x, y, _, _) = id
        return 0 <= x < self.width and 0 <= y < self.height

    def in_run(self, id):
        (_, _, _, i) = id
        return 0 <= i < 10

    def neighbors(self, id):
        (x, y, d, n) = id

        neighbors = []
        for dn in turns[d]:
            if n < 3 and (dn != d):
                continue
            nn = n + 1 if (dn == d) else 0
            (xs, ys) = direction[dn]
            neighbors.append((x + xs, y + ys, dn, nn))
        neighbors = filter(self.in_bounds, neighbors)
        neighbors = filter(self.in_run, neighbors)
        return neighbors


def heuristic(a, b):
    (x1, y1) = a
    (x2, y2) = b
    return abs(x1 - x2) + abs(y1 - y2)


def a_star_search(graph, start, goal):
    frontier = []
    heapq.heappush(frontier, (0, start))
    cost_so_far = {}
    cost_so_far[start] = 0
    while frontier:
        cost, current = heapq.heappop(frontier)
        # print(f"Visiting: {current}")
        if (current[0:2]) == goal:
            if current[-1] > 4:
                print(f"found goal {current}")
                break

        for next in graph.neighbors(current):
            new_cost = cost + graph.cost(current, next)
            if new_cost < cost_so_far.get(next, float("inf")):
                cost_so_far[next] = new_cost
                priority = new_cost  # + heuristic(next[0:2], goal)
                heapq.heappush(frontier, (priority, next))
    return cost_so_far[current]


with open("../input/day17.txt") as f:
    grid = [line.strip() for line in f.readlines()]
    graph = WeightedGrid(grid)
    h = len(grid)
    w = len(grid[0])

# part 2
start = (0, 0, "v", 0)
cost = a_star_search(graph, start, (w - 1, h - 1))
print(cost)
