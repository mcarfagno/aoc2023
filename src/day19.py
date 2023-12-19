import re

file = "../input/day19.txt"
workflows, parts = open(file).read().strip().split("\n\n")


def parse_workflow(workflow):
    x = re.findall(r"([a-z]+)\{(.+)\}", workflow)[0]
    return (x[0], x[1].split(","))


workflows = [parse_workflow(workflow) for workflow in workflows.split("\n")]
dw = dict(workflows)
dw["A"] = ["A"]
dw["R"] = ["R"]


def parse_part(part):
    x = re.findall(r"[x|m|a|s]=(\d+)", part)
    x = list(map(int, x))
    return {"x": x[0], "m": x[1], "a": x[2], "s": x[3]}


parts = [parse_part(part) for part in parts.split("\n")]


def process_workflow(id, part):
    # print(f"part: {part}, wf: {id} -> {dw[id]}")
    for rule in dw[id]:
        if rule == "R":
            return False
        if rule == "A":
            return True
        if "<" in rule:
            p, v, w = re.findall(r"([a-z]).(\d+):(.+)", rule)[0]
            if part[p] < int(v):
                return process_workflow(w, part)
            else:
                continue
        if ">" in rule:
            p, v, w = re.findall(r"([a-z]).(\d+):(.+)", rule)[0]
            if part[p] > int(v):
                return process_workflow(w, part)
            else:
                continue

        # default
        return process_workflow(rule, part)


# part 1
A = [p for p in parts if process_workflow("in", p)]
A = [sum(x.values()) for x in A]
print(sum(A))
