import heapq
from enum import Enum


class direction(Enum):
    UP = 1
    DOWN = 2
    LEFT = 3
    RIGHT = 4


class WeightedGrid:
    def __init__(self, grid):
        self.height = len(grid)
        self.width = len(grid[0])
        self.weights = {}
        for y in range(self.height):
            for x in range(self.width):
                self.weights[(x, y)] = int(grid[y][x])

        # direction -> next x y
        self.dirs = {
            direction.RIGHT: (+1, 0),
            direction.LEFT: (-1, 0),
            direction.UP: (0, +1),
            direction.DOWN: (0, -1),
        }

    def cost(self, from_node, to_node):
        (x, y, _, _) = to_node
        return self.weights.get((x, y))

    def in_bounds(self, id):
        (x, y, _, _) = id
        return 0 <= x < self.width and 0 <= y < self.height

    def next_dir(self, d, n):
        if n < 3:
            match d:
                case direction.RIGHT:
                    return (
                        (direction.RIGHT, n + 1),
                        (direction.DOWN, 0),
                        (direction.UP, 0),
                    )
                case direction.LEFT:
                    return (
                        (direction.LEFT, n + 1),
                        (direction.DOWN, 0),
                        (direction.UP, 0),
                    )
                case direction.DOWN:
                    return (
                        (direction.LEFT, 0),
                        (direction.DOWN, n + 1),
                        (direction.RIGHT, 0),
                    )
                case direction.UP:
                    return (
                        (direction.LEFT, 0),
                        (direction.UP, n + 1),
                        (direction.RIGHT, 0),
                    )

        else:
            match d:
                case direction.RIGHT:
                    return ((direction.DOWN, 0), (direction.UP, 0))
                case direction.LEFT:
                    return ((direction.DOWN, 0), (direction.UP, 0))
                case direction.DOWN:
                    return ((direction.LEFT, 0), (direction.RIGHT, 0))
                case direction.UP:
                    return ((direction.LEFT, 0), (direction.RIGHT, 0))

    def neighbors(self, id):
        (x, y, d, n) = id
        # n+d -> next d next n
        neighbors = []
        for dn, nn in self.next_dir(d, n):
            (xs, ys) = self.dirs[dn]
            neighbors.append((x + xs, y + ys, dn, nn))
        return filter(self.in_bounds, neighbors)


def heuristic(a, b):
    (x1, y1) = a
    (x2, y2) = b
    return abs(x1 - x2) + abs(y1 - y2)


def a_star_search(graph, start, goal):
    frontier = []
    heapq.heappush(frontier, (0, start))
    cost_so_far = {}
    cost_so_far[start[0:2]] = 0
    while frontier:
        _, current = heapq.heappop(frontier)
        # print(f"Visiting: {current}")
        if (current[0:2]) == goal:
            break

        for next in graph.neighbors(current):
            new_cost = cost_so_far[current[0:2]] + graph.cost(current, next)
            if next[0:2] not in cost_so_far or new_cost < cost_so_far[next[0:2]]:
                cost_so_far[next[0:2]] = new_cost
                priority = new_cost + heuristic(next[0:2], goal)
                heapq.heappush(frontier, (priority, next))
    return cost_so_far[current[0:2]]


with open("../test/day17.txt") as f:
    grid = [line.strip() for line in f.readlines()]
    graph = WeightedGrid(grid)
    h = len(grid)
    w = len(grid[0])

# part 1
start = (0, 0, direction.RIGHT, 0)
cost = a_star_search(graph, start, (w - 1, h - 1))
print(cost)
