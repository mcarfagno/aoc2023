import heapq

# CRUCIBLE (p1)
# MIN_STRAIGHT = 0
# MAX_STRAIGHT = 3

# ULTRA CRUCIBLE (p2)
MIN_STRAIGHT = 3
MAX_STRAIGHT = 10

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

    def in_max_straight(self, id):
        (_, _, _, n) = id
        return 0 <= n < MAX_STRAIGHT

    def in_min_straight(self, id, prev_id):
        (_, _, d, n) = prev_id
        (_, _, dn, nn) = id
        return not (n < MIN_STRAIGHT and (dn != d))

    def neighbors(self, id):
        (x, y, d, n) = id

        neighbors = []
        for dn in turns[d]:
            nn = n + 1 if (dn == d) else 0
            (xs, ys) = direction[dn]
            neighbors.append((x + xs, y + ys, dn, nn))
        neighbors = filter(self.in_bounds, neighbors)
        neighbors = filter(self.in_max_straight, neighbors)
        neighbors = filter(lambda x: self.in_min_straight(x, id), neighbors)
        return neighbors


def heuristic(a, b):
    (x1, y1) = a
    (x2, y2) = b
    return abs(x1 - x2) + abs(y1 - y2)


def a_star_search(graph, start, goal):
    frontier = []

    # start configurations
    s1 = (start[0], start[1], ">", 0)
    s2 = (start[0], start[1], "v", 0)
    heapq.heappush(frontier, (0, s1))
    heapq.heappush(frontier, (0, s2))

    cost_so_far = {}
    cost_so_far[s1] = 0
    cost_so_far[s2] = 0

    while frontier:
        cost, current = heapq.heappop(frontier)
        # print(f"Visiting: {current}")

        if (current[0:2]) == goal:
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

# part 1/2
loss = a_star_search(graph, (0, 0), (w - 1, h - 1))
print(loss)
