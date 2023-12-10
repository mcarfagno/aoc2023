from collections import deque


def breadth_first_search(graph, start):
    frontier = deque()
    frontier.append(start)
    reached = {}
    reached[start] = True

    steps = {}
    steps[start] = 0

    while frontier:
        current = frontier.popleft()
        # print(f"  Visiting {current}")
        # print(f"  after {steps[current]} steps")
        for next in graph[current]:
            if next not in reached:
                frontier.append(next)
                reached[next] = True
                steps[next] = steps[current] + 1

    # part 1
    print(max(steps.values()))

    # part 2


with open("../input/day10.txt") as f:
    grid = [line.strip() for line in f]
    graph = {}
    start = None
    for yi, line in enumerate(grid):
        for xi, c in enumerate(line):
            if c == "S":
                start = (xi, yi)
                graph[start] = [(xi, yi - 1), (xi, yi + 1)]
            else:
                match c:
                    case "|":
                        neighbors = [(xi, yi + 1), (xi, yi - 1)]
                    case "-":
                        neighbors = [(xi + 1, yi), (xi - 1, yi)]
                    case "L":
                        neighbors = [(xi, yi - 1), (xi + 1, yi)]
                    case "J":
                        neighbors = [(xi, yi - 1), (xi - 1, yi)]
                    case "7":
                        neighbors = [(xi, yi + 1), (xi - 1, yi)]
                    case "F":
                        neighbors = [(xi, yi + 1), (xi + 1, yi)]
                    case _:
                        neighbors = []
                graph[(xi, yi)] = neighbors

breadth_first_search(graph, start)
